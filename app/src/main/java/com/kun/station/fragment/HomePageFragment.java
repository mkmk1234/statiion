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
    List<Integer> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);
        adapter = new MyLoopViewPageAdapter();
        viewpager.setAdapter(adapter);
        viewpager.setAutoPagerFlipAnimaionTime(400);
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
        data.add(R.drawable.home_viewpager_one);
        data.add(R.drawable.home_viewpager_two);
        data.add(R.drawable.home_viewpager_three);
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

            ((ImageView) convertView).setImageResource(data.get(position));
//            Glide.with(getContext())
//                    .load(data.get(position))
//                    .crossFade()
//                    .into((ImageView) convertView);
            return convertView;
        }
    }
}
