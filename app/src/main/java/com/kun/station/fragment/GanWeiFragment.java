package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @Bind(R.id.backgroud)
    MyView myview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ganwei, container, false);
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
                showFragment(PersonnelFragment.class, null, R.id.layout);
                myview.setData(fst.getLeft(), fst.getTop(), fst.getBottom(), fst.getRight());
                break;
            case R.id.sec:
                showFragment(PersonnelFragment.class, null, R.id.layout);
                break;
            case R.id.thd:
                showFragment(PersonnelFragment.class, null, R.id.layout);
                break;
            case R.id.forth:
                showFragment(PersonnelFragment.class, null, R.id.layout);
                break;
        }
    }
}
