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
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kun.station.R;
import com.kun.station.model.NoticeModel;
import com.senab.photoview.PhotoView;
import com.senab.photoview.PhotoViewAttacher;

import butterknife.Bind;

/**
 * Created by kun on 16/6/3.
 */
public class CustomPop extends PopupWindow implements View.OnClickListener{
    Activity mActivity;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.web_content)
    WebView webContent;
    @Bind(R.id.iv_img)
    PhotoView imageView;
    @Bind(R.id.ll_txt)
    LinearLayout llTxt;
    View rootView;


    private Type mShowType;
    private Bitmap mBitmap;
    private int mResource = -1;
    private NoticeModel noticeModel;
    public enum Type {
        Type_Txt, Type_Img
    }

    public CustomPop(Activity activity) {
        super(activity);
        mActivity = activity;
        initView(activity);
        mShowType = Type.Type_Txt;
    }

    public CustomPop(Activity activity, Type type) {
        super(activity);
        mShowType = type;
        mActivity = activity;
        initView(activity);
    }

    public CustomPop(Activity activity, Type type, NoticeModel noticeModel) {
        super(activity);
        mShowType = type;
        mActivity = activity;
        this.noticeModel = noticeModel;
        initView(activity);
    }
    public void setImageBitmap(Bitmap bitmap) {
        mShowType = Type.Type_Img;
        mBitmap = bitmap;
    }

    public void setImageResource(int resource) {
        mShowType = Type.Type_Img;
        mResource = resource;
    }

    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.pop_text, null);
        rootView.setOnClickListener(this);
        setContentView(rootView);
        llTxt = (LinearLayout) rootView.findViewById(R.id.ll_txt);
        imageView = (PhotoView) rootView.findViewById(R.id.iv_img);
        webContent = (WebView) rootView.findViewById(R.id.web_content);
        txtTitle = (TextView) rootView.findViewById(R.id.txt_title);
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
                CustomPop.super.dismiss();
            }
        });
        rootView.startAnimation(animation);
    }

    public void show() {
        if (mShowType == Type.Type_Img) {
            llTxt.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    CustomPop.this.dismiss();
                }

                @Override
                public void onOutsidePhotoTap() {
                    CustomPop.this.dismiss();
                }
            });
            if (mBitmap != null) {
                imageView.setImageBitmap(mBitmap);
            } else if (mResource != -1) {
                imageView.setImageResource(mResource);
            }
        } else {
            if (noticeModel != null) {
                txtTitle.setText(noticeModel.title);
                webContent.getSettings().setDefaultTextEncodingName("UTF -8");
                webContent.loadDataWithBaseURL(null, noticeModel.noticeContent, "text/html", "utf-8", null);
//                webContent.loadData(URLEncoder.encode(noticeModel.noticeContent), "text/html; charset=UTF-8", null);

            }
        }
        Animation trans = AnimationUtils.loadAnimation(mActivity, R.anim.anim_enter);
        trans.setDuration(300);
        trans.setInterpolator(new AccelerateDecelerateInterpolator());
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        rootView.startAnimation(trans);
    }
}
