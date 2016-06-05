package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/6/4.
 */
public class PersonnelFragment extends BaseFragment {
    @Bind(R.id.grid_personnel)
    GridView gridPersonnel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personnel, null, false);
        ButterKnife.bind(this, view);
        gridPersonnel.setAdapter(new PersonnelGridAdapter());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class PersonnelGridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 16;
        }

        @Override
        public Object getItem(int position) {
            return "";
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_personnel, parent, false);
            return convertView;
        }
    }
}
