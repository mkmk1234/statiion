package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.widget.MyView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kun on 16/5/25.
 */
public class GanWeiFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.top)
    LinearLayout top;
    @Bind(R.id.fst)
    LinearLayout fst;
    @Bind(R.id.sec)
    LinearLayout sec;
    @Bind(R.id.thd)
    LinearLayout thd;
    @Bind(R.id.forth)
    LinearLayout forth;
    @Bind(R.id.left)
    LinearLayout left;
    @Bind(R.id.layout)
    FrameLayout layout;
    @Bind(R.id.myview)
    MyView myview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ganwei, container, false);
        ButterKnife.bind(this, view);
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                onClick(fst);
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.fst, R.id.sec, R.id.thd, R.id.forth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fst:
                layout.removeAllViews();
                layout.addView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_renyuan, null));
                myview.setData(fst.getLeft(), fst.getTop(), fst.getBottom(), fst.getRight());
                break;
            case R.id.sec:
                layout.removeAllViews();
                layout.addView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_tubiao, null));
                myview.setData(sec.getLeft(), sec.getTop(), sec.getBottom(), sec.getRight());
                break;
            case R.id.thd:
                myview.setData(thd.getLeft(), thd.getTop(), thd.getBottom(), thd.getRight());
                break;
            case R.id.forth:
                myview.setData(forth.getLeft(), forth.getTop(), forth.getBottom(), forth.getRight());
                break;
        }
    }
}
