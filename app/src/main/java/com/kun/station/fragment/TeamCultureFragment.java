package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/6/19.
 */
public class TeamCultureFragment extends BaseFragment {
    @Bind(R.id.teamconcept)
    TextView teamconcept;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teamculture, container, false);
        ButterKnife.bind(this, view);
        teamconcept.setText(getStringParam("teamConcept") + "");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
