package com.kun.station;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kun on 16/5/25.
 */
public class GanWeiFragment extends Fragment implements View.OnClickListener {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ganwei, container, false);
        ButterKnife.bind(this, view);
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
                fst.setBackgroundColor(Color.parseColor("#ffffff"));
                sec.setBackgroundColor(Color.parseColor("#e1e1e1"));
                thd.setBackgroundColor(Color.parseColor("#e1e1e1"));
                forth.setBackgroundColor(Color.parseColor("#e1e1e1"));
                layout.removeAllViews();
                layout.addView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_renyuan, null));
                break;
            case R.id.sec:
                fst.setBackgroundColor(Color.parseColor("#e1e1e1"));
                sec.setBackgroundColor(Color.parseColor("#ffffff"));
                thd.setBackgroundColor(Color.parseColor("#e1e1e1"));
                forth.setBackgroundColor(Color.parseColor("#e1e1e1"));
                layout.removeAllViews();
                layout.addView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_tubiao, null));
                break;
            case R.id.thd:
                fst.setBackgroundColor(Color.parseColor("#e1e1e1"));
                sec.setBackgroundColor(Color.parseColor("#e1e1e1"));
                thd.setBackgroundColor(Color.parseColor("#ffffff"));
                forth.setBackgroundColor(Color.parseColor("#e1e1e1"));
                break;
            case R.id.forth:
                fst.setBackgroundColor(Color.parseColor("#e1e1e1"));
                sec.setBackgroundColor(Color.parseColor("#e1e1e1"));
                thd.setBackgroundColor(Color.parseColor("#e1e1e1"));
                forth.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
    }
}
