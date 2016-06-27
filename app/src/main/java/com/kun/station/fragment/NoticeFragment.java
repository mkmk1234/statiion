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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.db.DbManager;
import com.kun.station.model.NoticeModel;
import com.kun.station.util.Log;
import com.kun.station.widget.CustomPop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/6/3.
 */
public class NoticeFragment extends BaseFragment {

    @Bind(R.id.list)
    ListView listview;
    List<NoticeModel> datas;
    List<NoticeModel> datastemp;
    ListAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            datas.addAll(datastemp);
            datas.addAll(DbManager.getInstace(getContext()).getNoitce());
            saveNotice();
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
                NoticeModel noticeModel = adapter.getItem(position);
                noticeModel.readTime = getDate();
                noticeModel.hasRead = true;
                DbManager.getInstace(getContext()).updateNotice(noticeModel);
                new CustomPop(getActivity()).show();
                adapter.notifyDataSetChanged();
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

    private void saveNotice() {
        for (int i = 0; i < datastemp.size(); i++) {
            DbManager.getInstace(getContext()).insertNotice(datastemp.get(i));
        }
    }

    private void getData() {
        datastemp = new ArrayList<>();
        datas = new ArrayList<>();
        Log.i("sss", "size" + datas.size());
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(100);
                    for (int i = 0; i < 10; i++) {
                        datastemp.add(new NoticeModel(datas.size() + i, "这是一个公告", "第" + (i + 1) + "个公告", "公告内容", false));
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
            if (datas == null) {
                return 0;
            }
            return datas.size();
        }

        @Override
        public NoticeModel getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_new, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.title.setText(getItem(position).title);
            viewHolder.content.setText(getItem(position).detail);
            if (getItem(position).hasRead) {
                viewHolder.title.setTextColor(Color.parseColor("#9b9b9b"));
                viewHolder.txtRead.setTextColor(Color.parseColor("#9b9b9b"));
                viewHolder.txtRead.setText("已阅");
                viewHolder.txtReadTime.setText(getItem(position).readTime);
                viewHolder.txtReadTime.setVisibility(View.VISIBLE);
                viewHolder.imgRight.setVisibility(View.GONE);
            } else {
                viewHolder.title.setTextColor(Color.parseColor("#333333"));
                viewHolder.txtRead.setTextColor(Color.parseColor("#333333"));
                viewHolder.txtRead.setText("未读");
                viewHolder.txtReadTime.setVisibility(View.GONE);
                viewHolder.imgRight.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.img_recode)
            ImageView imgRecode;
            @Bind(R.id.title)
            TextView title;
            @Bind(R.id.content)
            TextView content;
            @Bind(R.id.txt_read)
            TextView txtRead;
            @Bind(R.id.txt_read_time)
            TextView txtReadTime;
            @Bind(R.id.img_right)
            ImageView imgRight;
            @Bind(R.id.layout_read_state)
            LinearLayout layoutReadState;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
