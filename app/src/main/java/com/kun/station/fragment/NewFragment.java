package com.kun.station.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.model.NoticeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/5/25.
 */
public class NewFragment extends BaseFragment {


    @Bind(R.id.list)
    ListView listview;
    List<NoticeModel> datas;
    ListAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        ButterKnife.bind(this, view);
        adapter = new ListAdapter();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view.findViewById(R.id.title)).setTextColor(Color.parseColor("#9b9b9b"));
                ((TextView) view.findViewById(R.id.txt_read)).setTextColor(Color.parseColor("#9b9b9b"));
                ((TextView) view.findViewById(R.id.txt_read)).setText("已阅");
                ((TextView) view.findViewById(R.id.txt_read_time)).setText(getDate());
                ((TextView) view.findViewById(R.id.txt_read_time)).setVisibility(View.VISIBLE);
                view.findViewById(R.id.img_right).setVisibility(View.GONE);
            }
        });
        getData();
        return view;
    }

    private String getDate() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(d);
    }

    private void getData() {
        datas = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1500);
                    for (int i = 0; i < 10; i++) {
                        datas.add(new NoticeModel(1, "这是一个公告", "第" + i + 1 + "个公告", "公告内容"));
                    }
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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