package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.model.HomeBannerModel;
import com.kun.station.network.NetworkApi;
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
    List<HomeBannerModel> data;


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
        NetworkApi.getHomeBanner(new Response.Listener<ArrayList<HomeBannerModel>>() {
            @Override
            public void onResponse(ArrayList<HomeBannerModel> response) {
                data.addAll(response);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT);
            }
        });
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

            Glide.with(getContext())
                    .load(data.get(position).getImageUrl())
                    .crossFade()
                    .error(R.drawable.imgerr)
                    .into((ImageView) convertView);
            return convertView;
        }
    }
}
