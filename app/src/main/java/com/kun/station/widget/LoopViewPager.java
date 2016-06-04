package com.kun.station.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;


public class LoopViewPager extends ViewPager {

    public static final int SCROLL_WHAT = 0;

    private long scroll_interval = 5000;

    private boolean isAutoScroll = false;

    private boolean isStopByTouch = false;

    private Handler handler;

    private OnDSPageChangeListener changeListener;

    private ViewGroup nestedParentView;

    public LoopViewPager(Context context) {
        super(context);
        init();
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        handler = new MyHandler();
        setOnPageChangeListener(new OnPageChangeListener() {

            private int scrollState;
            private int selectedPositon;

            @Override
            public final void onPageSelected(int position) {
                selectedPositon = position;
                scrollAjust(position);
                if (changeListener != null) {
                    changeListener.onPageSelected(getAdapter().getRealPosition(position));
                }
            }

            @Override
            public final void onPageScrollStateChanged(int state) {
                scrollState = state;
                scrollAjust(selectedPositon);
                if (changeListener != null) {
                    changeListener.onPageScrollStateChanged(state);
                }
            }

            @Override
            public final void onPageScrolled(int position, float offset, int offsetPixels) {
                if (changeListener != null) {
                    changeListener.onPageScrolled(getAdapter().getRealPosition(position), offset, offsetPixels);
                }
            }

            public void scrollAjust(int position) {
                do {
                    if (!isLoop()) {
                        break;
                    }
                    if (scrollState != SCROLL_STATE_IDLE) {
                        break;
                    }
                    if (position == 0) {
                        setCurrentItem(getAdapter().getCount() - 2, false);
                    } else if (position == getAdapter().getCount() - 1) {
                        setCurrentItem(1, false);
                    }
                } while (false);
            }
        });
    }

    public void setInterval(long interval) {
        this.scroll_interval = interval;
    }

    public void startAutoScroll() {
        isAutoScroll = true;
        sendScrollMessage(scroll_interval);
    }

    public void startAutoScroll(int delayTimeInMills) {
        startAutoScroll(delayTimeInMills, scroll_interval);
    }

    public void startAutoScroll(int delayTimeInMills, long interval) {
        isAutoScroll = true;
        scroll_interval = interval;
        sendScrollMessage(delayTimeInMills);
    }

    /**
     * stop auto scroll
     */
    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHAT);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);

        if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
            isStopByTouch = true;
            stopAutoScroll();
        } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
            startAutoScroll();
        }
        if (nestedParentView != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setNestedParentView(ViewGroup nestedParentView) {
        this.nestedParentView = nestedParentView;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    PagerAdapter adapter = getAdapter();
                    if (adapter == null || adapter.getCount() <= 1) {
                        return;
                    }
                    int totalCount = adapter.getCount();
                    int nextItem = getCurrentItem() + 1;
                    if (nextItem < totalCount) {
                        setCurrentItem(nextItem, true);
                        sendEmptyMessageDelayed(SCROLL_WHAT, scroll_interval);
                    }
                default:
                    break;
            }
        }
    }

    private boolean isLoop() {
        try {
            LoopPageAdapter adapter = getAdapter();
            return adapter.canLoop();
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter instanceof LoopPageAdapter) {
            super.setAdapter(adapter);
            if (getAdapter().canLoop()) {
                setCurrentItem(1, false);
            }
            if (isAutoScroll) {
                startAutoScroll();
            }
        } else {
            throw new IllegalArgumentException("adapter must be instanceof LoopPageAdapter");
        }
    }

    public void setAutoPagerFlipAnimaionTime(int time) {
        if (time >= 300) {
            time = 300;
        }
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixSpeedScroll mScroller = new FixSpeedScroll(this.getContext(), new AccelerateInterpolator(), time);
            mField.set(this, mScroller);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private class FixSpeedScroll extends Scroller {
        private int animTime = 300;

        public FixSpeedScroll(Context context) {
            super(context);
        }

        public FixSpeedScroll(Context context, int animTime) {
            super(context);
            this.animTime = animTime;
        }

        public FixSpeedScroll(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixSpeedScroll(Context context, Interpolator interpolator, int animTime) {
            super(context, interpolator);
            this.animTime = animTime;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, animTime);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, animTime);
        }

        public void setmDuration(int animTime) {
            this.animTime = animTime;
        }
    }

    public LoopPageAdapter getAdapter() {
        if (null == super.getAdapter()) {
            return null;
        }
        return (LoopPageAdapter) super.getAdapter();
    }

    public void setOnDSPageChangeListener(OnDSPageChangeListener listener) {
        changeListener = listener;
    }

    public static interface OnDSPageChangeListener {
        public abstract void onPageScrolled(int position, float offset, int offsetPixels);

        public abstract void onPageSelected(int selectedIndex);

        public abstract void onPageScrollStateChanged(int state);
    }

}
