package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;

/**
 * Created by kun on 16/6/19.
 */
public class TeamCultureFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_teamculture, container, false);
    }
}
