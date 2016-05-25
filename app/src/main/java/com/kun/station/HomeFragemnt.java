package com.kun.station;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
public class HomeFragemnt extends Fragment implements View.OnClickListener {
    @Bind(R.id.fst)
    LinearLayout fst;
    @Bind(R.id.sec)
    LinearLayout sec;
    @Bind(R.id.thd)
    LinearLayout thd;
    @Bind(R.id.forth)
    LinearLayout forth;
    @Bind(R.id.home_container)
    FrameLayout homeContainer;
    @Bind(R.id.righ_layout)
    LinearLayout righLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        showFragment(NewFragment.class);
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
                showFragment(NewFragment.class);
                break;
            case R.id.sec:
                showFragment(HFragment.class);
                break;
            case R.id.thd:
                showFragment(NewFragment.class);
                break;
            case R.id.forth:
                showFragment(NewFragment.class);
                break;
        }
    }

    private void showFragment(Class<?> clss) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.home_container, Fragment.instantiate(getActivity(), clss.getName(), null));

        ft.commitAllowingStateLoss();
    }
}
