package com.kun.station.widget;

import android.view.View;
import android.view.ViewGroup;

public abstract class LoopPageAdapter extends RecyclingPagerAdapter {

    @Override
    public final int getCount() {
        int realCount = getItemCount();
        if (canLoop()) {
            return realCount + 2;
        }
        return getItemCount();
    }

    public final boolean canLoop() {
        return isLoop() && getItemCount() > 1;
    }

    protected boolean isLoop() {
        return true;
    }

    public int getRealPosition(int position) {
        int realPosition = position;
        if (canLoop()) {
            if (position == 0) {
                realPosition = getCount() - 2 - 1;
            } else if (position == getCount() - 1) {
                realPosition = 0;
            } else {
                realPosition = position - 1;
            }
        }
        return realPosition;
    }

    @Override
    public final int getItemViewType(int position) {
        return getItemViewFlag(getRealPosition(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return getItemView(getRealPosition(position), convertView, container);
    }

    public int getItemViewFlag(int position) {
        return 0;
    }

    public abstract int getItemCount();

    public abstract View getItemView(int position, View convertView, ViewGroup container);

}
