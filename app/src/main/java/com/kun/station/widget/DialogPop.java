package com.kun.station.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
public class DialogPop extends PopupWindow implements View.OnClickListener {
    Activity mActivity;
    TextView confirm;
    View rootView;

    private Type mShowType;
    private Bitmap mBitmap;
    private int mResource = -1;

    public enum Type {
        Type_Txt, Type_Img
    }

    public DialogPop(Activity activity) {
        super(activity);
        mActivity = activity;
        initView(activity);
    }


    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.pop_dialog, null);
        setContentView(rootView);
//        ButterKnife.bind(rootView);
        confirm = (TextView) rootView.findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setTouchable(true);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
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

    public void show() {
        Animation trans = AnimationUtils.loadAnimation(mActivity, R.anim.anim_enter);
        trans.setDuration(300);
        trans.setInterpolator(new AccelerateDecelerateInterpolator());
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        rootView.startAnimation(trans);
    }
}
