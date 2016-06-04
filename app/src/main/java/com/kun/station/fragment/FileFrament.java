package com.kun.station.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.widget.CustomPop;

/**
 * Created by kun on 16/6/5.
 */
public class FileFrament extends BaseFragment {
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, null);
        mListView = (ListView) view.findViewById(R.id.list);
        mListView.setAdapter(new PictureAdapter(getActivity()));
        mListView.setOnItemClickListener(mItemClickListener);
        return view;
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CustomPop pop = new CustomPop(getActivity());
            pop.setImageResource(R.drawable.ic_test);
            pop.show();
        }
    };

    private class PictureAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public PictureAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mInflater.inflate(R.layout.item_file, null);
        }
    }
}
