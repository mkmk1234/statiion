package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.kun.station.R;
import com.kun.station.adapter.PageFragmentAdapter;
import com.kun.station.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/6/4.
 */
public class FileCombineFragment extends BaseFragment{
    private ViewPager mContentPager;
    private RadioGroup mRadioGroup;
    private List<Fragment> dataList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList.add(new FileFragment());
        dataList.add(new FileSearchFragment());
        dataList.add(new FileStoreFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_combine, null);
        mContentPager = (ViewPager) view.findViewById(R.id.vp_content);
        mContentPager.setAdapter(new PageFragmentAdapter(getFragmentManager(), dataList));
        mContentPager.addOnPageChangeListener(mPagerChangeListener);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_radio);
        mRadioGroup.setOnCheckedChangeListener(mCheckedChangeListener);
        return view;
    }

    private RadioGroup.OnCheckedChangeListener mCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_file:
                    mContentPager.setCurrentItem(0);
                    break;
                case R.id.rb_search:
                    mContentPager.setCurrentItem(1);
                    break;
                case R.id.rb_store:
                    mContentPager.setCurrentItem(2);
                    break;
            }
        }
    };

    private ViewPager.OnPageChangeListener mPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mRadioGroup.check(R.id.rb_file);
                    break;
                case 1:
                    mRadioGroup.check(R.id.rb_search);
                    break;
                case 2:
                    ((FileStoreFragment)dataList.get(2)).refreshData();
                    mRadioGroup.check(R.id.rb_store);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
