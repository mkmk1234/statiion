package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.widget.LoopPageAdapter;
import com.kun.station.widget.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/5/25.
 */
public class HomePageFragment extends BaseFragment {
    @Bind(R.id.viewpager)
    LoopViewPager viewpager;
    MyLoopViewPageAdapter adapter;
    List<String> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);
        adapter = new MyLoopViewPageAdapter();
        viewpager.setAdapter(adapter);
        viewpager.startAutoScroll();
        loadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadData() {
        data = new ArrayList<>();
        data.add("http://image.baidu.com/search/detail?ct=503316480&z=0&tn=baiduimagedetail&ipn=d&cl=2&cm=1&sc=0&lm=-1&ie=gbk&pn=0&rn=1&di=113882360460&ln=30&word=%CD%BC%C6%AC&os=665726132,356480541&cs=4236942158,2307642402&objurl=http%3A%2F%2Fpic32.nipic.com%2F20130829%2F12906030_124355855000_2.png&bdtype=0&simid=3444347259,284796380&fr=ala&ala=1&alatpl=others&pos=1");
        data.add("http://image.baidu.com/search/detail?ct=503316480&z=0&tn=baiduimagedetail&ipn=d&cl=2&cm=1&sc=0&lm=-1&ie=gbk&pn=0&rn=1&di=113882360460&ln=30&word=%CD%BC%C6%AC&os=665726132,356480541&cs=4236942158,2307642402&objurl=http%3A%2F%2Fpic32.nipic.com%2F20130829%2F12906030_124355855000_2.png&bdtype=0&simid=3444347259,284796380&fr=ala&ala=1&alatpl=others&pos=1");
        data.add("http://image.baidu.com/search/detail?ct=503316480&z=0&tn=baiduimagedetail&ipn=d&cl=2&cm=1&sc=0&lm=-1&ie=gbk&pn=0&rn=1&di=113882360460&ln=30&word=%CD%BC%C6%AC&os=665726132,356480541&cs=4236942158,2307642402&objurl=http%3A%2F%2Fpic32.nipic.com%2F20130829%2F12906030_124355855000_2.png&bdtype=0&simid=3444347259,284796380&fr=ala&ala=1&alatpl=others&pos=1");
        adapter.notifyDataSetChanged();
    }

    class MyLoopViewPageAdapter extends LoopPageAdapter {

        @Override
        public int getItemCount() {
            if (data == null) {
                return 0;
            }
            return data.size();
        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_loop_page, container, false);
            }
            ((ImageView) convertView).setImageResource(R.mipmap.ic_launcher);
//            Glide.with(getContext())
//                    .load(data.get(position))
//                    .crossFade()
//                    .into((ImageView) convertView);
            return convertView;
        }
    }
}
