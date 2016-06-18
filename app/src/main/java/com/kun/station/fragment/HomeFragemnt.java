package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/5/25.
 */
public class HomeFragemnt extends BaseFragment {
    @Bind(R.id.home_container)
    FrameLayout homeContainer;
    @Bind(R.id.righ_layout)
    LinearLayout righLayout;
    @Bind(R.id.top_menu)
    RadioGroup topMenu;
    @Bind(R.id.left_layout)
    LinearLayout leftLayout;
    @Bind(R.id.line_su)
    View lineSu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        topMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_fst:
                        Bundle b1 = new Bundle();
                        showFragment(HomePageFragment.class, b1);
                        break;
                    case R.id.rb_sec:
                        showFragment(NoticeFragment.class, null);
                        break;
                    case R.id.rb_thd:
                        showFragment(NewFragment.class, null);

                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void hasNew() {
        topMenu.check(R.id.rb_thd);
    }

    private void showFragment(Class<?> clss, Bundle b) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.home_container, Fragment.instantiate(getActivity(), clss.getName(), b));

        ft.commitAllowingStateLoss();
    }

    private void showDialog() {

    }
}
