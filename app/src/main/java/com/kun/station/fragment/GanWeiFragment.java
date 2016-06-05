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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ganwei, container, false);
        ButterKnife.bind(this, view);
        onClick(fst);
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
                fst.setBackgroundResource(R.drawable.img_pda_dirselect);
                sec.setBackgroundResource(0);
                thd.setBackgroundResource(0);
                forth.setBackgroundResource(0);
                break;
            case R.id.sec:
                showFragment(ChartHangingFragment.class, null, R.id.layout);
                sec.setBackgroundResource(R.drawable.img_pda_dirselect);
                fst.setBackgroundResource(0);
                thd.setBackgroundResource(0);
                forth.setBackgroundResource(0);
                break;
            case R.id.thd:
                showFragment(FileFrament.class, null, R.id.layout);
                thd.setBackgroundResource(R.drawable.img_pda_dirselect);
                sec.setBackgroundResource(0);
                fst.setBackgroundResource(0);
                forth.setBackgroundResource(0);
                break;
            case R.id.forth:
                showFragment(FileFrament.class, null, R.id.layout);
                forth.setBackgroundResource(R.drawable.img_pda_dirselect);
                sec.setBackgroundResource(0);
                thd.setBackgroundResource(0);
                fst.setBackgroundResource(0);
                break;
        }
    }
}
