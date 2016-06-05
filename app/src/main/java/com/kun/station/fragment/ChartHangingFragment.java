package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by kun on 16/6/5.
 */
public class ChartHangingFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_hang, container, false);
        ButterKnife.bind(this, view);
        showFragment(FileFrament.class, null, R.id.container);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
