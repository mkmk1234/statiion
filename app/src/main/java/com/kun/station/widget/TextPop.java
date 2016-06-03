package com.kun.station.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kun.station.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/6/3.
 */
public class TextPop extends PopupWindow {
    Activity mActivity;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_content)
    TextView txtContent;

    public TextPop(Activity activity) {
        super(activity);
        mActivity = activity;
        initView(activity);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.pop_text, null);
        ButterKnife.bind(rootView);
        setContentView(rootView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setTouchable(true);
    }

    @Override
    public void dismiss() {
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.anim_exit);
        animation.setDuration(200);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
    }

    public void show() {
        Animation trans = AnimationUtils.loadAnimation(mActivity, R.anim.anim_enter);
        trans.setDuration(260);
        trans.setInterpolator(new AccelerateDecelerateInterpolator());
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
}
