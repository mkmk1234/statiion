package com.kun.station.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.kun.station.R;
import com.kun.station.adapter.PageFragmentAdapter;
import com.kun.station.base.BaseFragment;
import com.kun.station.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/6/4.
 */
public class FileCombineFragment extends BaseFragment {
    private ViewPager mContentPager;
    private RadioGroup mRadioGroup;
    private List<Fragment> dataList = new ArrayList<>();
    private CatalogFragment mCatalogFragment;
    private FileSearchFragment mFileSearchFragment;
    private FileStoreFragment mFileStoreFragment;
    private String rootPath;
    private Fragment currentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList.clear();
        if (getArguments() != null){
            rootPath = getArguments().getString(CatalogFragment.ExtraPATH);
        }
        mCatalogFragment = new CatalogFragment();
        dataList.add(mCatalogFragment);
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
        addItemFragment(0);
        return view;
    }

    private void addItemFragment(int position) {
        Fragment itemFragment = null;
        Bundle b = new Bundle();
        b.putString(CatalogFragment.ExtraPATH, TextUtils.isEmpty(rootPath) ? FileUtil.getExternalDir().getPath() : rootPath);
        switch (position) {
            case 0:
                itemFragment = Fragment.instantiate(getActivity(), CatalogFragment.class.getName(), b);
                break;
            case 1:
                itemFragment = Fragment.instantiate(getActivity(), FileSearchFragment.class.getName(), b);
                break;
            case 2:
                itemFragment = Fragment.instantiate(getActivity(), FileStoreFragment.class.getName(), null);
                break;
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll_container, itemFragment).commit();
        currentFragment = itemFragment;
    }

    private RadioGroup.OnCheckedChangeListener mCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_file:
                    addItemFragment(0);
                    mContentPager.setCurrentItem(0);
                    break;
                case R.id.rb_search:
                    addItemFragment(1);
                    mContentPager.setCurrentItem(1);
                    break;
                case R.id.rb_store:
                    addItemFragment(2);
                    mContentPager.setCurrentItem(2);
                    break;
            }
        }
    };

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

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
                    mRadioGroup.check(R.id.rb_store);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}
