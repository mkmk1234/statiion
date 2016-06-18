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

/**
 * Created by kun on 16/6/5.
 */
public class DialogPop extends PopupWindow {
    Activity mActivity;
    TextView confirm;
    TextView message;
    TextView cancel;
    View rootView;
    View btn_divider;

    public DialogPop(Activity activity, boolean hasCancel) {
        super(activity);
        mActivity = activity;
        initView(activity, hasCancel);
    }


    private void initView(Context context, boolean hasCancel) {
        rootView = LayoutInflater.from(context).inflate(R.layout.pop_dialog, null);
        setContentView(rootView);
        confirm = (TextView) rootView.findViewById(R.id.confirm);
        message = (TextView) rootView.findViewById(R.id.message);
        cancel = (TextView) rootView.findViewById(R.id.cancel);
        btn_divider = rootView.findViewById(R.id.btn_divider);
        if (hasCancel) {
            btn_divider.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
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
                DialogPop.super.dismiss();
            }
        });
        rootView.startAnimation(animation);
    }

    public void show(String messageStr, View.OnClickListener onClickListener) {
        message.setText(messageStr);
        confirm.setOnClickListener(onClickListener);
        Animation trans = AnimationUtils.loadAnimation(mActivity, R.anim.anim_enter);
        trans.setDuration(300);
        trans.setInterpolator(new AccelerateDecelerateInterpolator());
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        rootView.startAnimation(trans);
    }
}
