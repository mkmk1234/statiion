package com.kun.station;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.text2)
    TextView text2;
    @Bind(R.id.text3)
    TextView text3;
    @Bind(R.id.text4)
    TextView text4;
    @Bind(R.id.top_menu)
    LinearLayout topMenu;
    @Bind(R.id.left_layout)
    LinearLayout leftLayout;
    @Bind(R.id.line_su)
    View lineSu;
    @Bind(R.id.layout_botoom)
    LinearLayout layoutBotoom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
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
                showFragment(NewFragment.class);
                fst.setBackgroundResource(R.drawable.yellow);
                sec.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                thd.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                forth.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                text1.setTextColor(Color.parseColor("#333333"));
                text2.setTextColor(Color.parseColor("#ffffff"));
                text3.setTextColor(Color.parseColor("#ffffff"));
                text4.setTextColor(Color.parseColor("#ffffff"));
                break;
            case R.id.sec:
                showFragment(HFragment.class);
                sec.setBackgroundResource(R.drawable.yellow);
                fst.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                thd.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                forth.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                text1.setTextColor(Color.parseColor("#ffffff"));
                text2.setTextColor(Color.parseColor("#333333"));
                text3.setTextColor(Color.parseColor("#ffffff"));
                text4.setTextColor(Color.parseColor("#ffffff"));
                break;
            case R.id.thd:
                showFragment(NewFragment.class);
                thd.setBackgroundResource(R.drawable.yellow);
                sec.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                fst.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                forth.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                text1.setTextColor(Color.parseColor("#ffffff"));
                text2.setTextColor(Color.parseColor("#ffffff"));
                text3.setTextColor(Color.parseColor("#333333"));
                text4.setTextColor(Color.parseColor("#ffffff"));
                break;
            case R.id.forth:
                showFragment(NewFragment.class);
                forth.setBackgroundResource(R.drawable.yellow);
                sec.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                thd.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                fst.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                text1.setTextColor(Color.parseColor("#ffffff"));
                text2.setTextColor(Color.parseColor("#ffffff"));
                text3.setTextColor(Color.parseColor("#ffffff"));
                text4.setTextColor(Color.parseColor("#333333"));
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
