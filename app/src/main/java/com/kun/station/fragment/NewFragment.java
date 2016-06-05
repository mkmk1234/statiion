package com.kun.station.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/5/25.
 */
public class NewFragment extends BaseFragment {


    @Bind(R.id.list)
    ListView list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        ButterKnife.bind(this, view);
        Toast.makeText(getActivity(), "new ", Toast.LENGTH_SHORT).show();
        list.setAdapter(new ListAdapter());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view.findViewById(R.id.title)).setTextColor(Color.parseColor("#9b9b9b"));
                view.findViewById(R.id.txt_read).setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
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
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_new, parent, false);
            }
            return convertView;
        }
    }
}