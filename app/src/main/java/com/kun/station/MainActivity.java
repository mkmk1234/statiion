package com.kun.station;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.google.gson.reflect.TypeToken;
import com.kun.station.base.BaseActivity;
import com.kun.station.fragment.CatalogFragment;
import com.kun.station.fragment.FileCombineFragment;
import com.kun.station.fragment.GanWeiFragment;
import com.kun.station.fragment.HomeFragemnt;
import com.kun.station.fragment.ToolsFragment;
import com.kun.station.model.Model;
import com.kun.station.response.MenuItemResponse;
import com.kun.station.util.FileUtil;
import com.kun.station.widget.DialogPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseActivity {

    @Bind(R.id.left_menu_list)
    ListView leftMenuList;
    @Bind(R.id.detail_layout)
    FrameLayout detailLayout;
    List<Model> list;

    private ListAdapter mAdapter;
    private FileCombineFragment mFileCombineFragment;
    private HomeFragemnt homeFragemnt;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final DialogPop dialogPop = new DialogPop(MainActivity.this);
            dialogPop.show("您有新文件更新，请及时查看。", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeFragemnt != null && homeFragemnt.isResumed()) {
                        homeFragemnt.hasNew();
                    }
                    dialogPop.dismiss();
                }
            });

        }
    };
    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_main);
    }

    private void initData() {

        ArrayList<MenuItemResponse> menuList = MyApplication.mGson.fromJson(FileUtil.loadRawString(this, R.raw.localdata), new TypeToken<ArrayList<MenuItemResponse>>(){}.getType());


        mAdapter = new ListAdapter();
        list = new ArrayList<>();
        list.add(new Model("系统首页", R.drawable.main_function_home_down, R.drawable.main_function_home));
        list.add(new Model("企业简介", R.drawable.main_function_enterprise_down, R.drawable.main_function_enterprise));
        list.add(new Model("规章资料", R.drawable.main_function_book_down, R.drawable.main_function_book));
        list.add(new Model("岗位建设", R.drawable.main_function_post_constrution_down, R.drawable.main_function_post_constrution));
        list.add(new Model("作业标准", R.drawable.main_function_standard_down, R.drawable.main_function_standard));
        list.add(new Model("运输生产", R.drawable.main_function_pda_down, R.drawable.main_function_pda));
        list.add(new Model("运输生产", R.drawable.main_function_pda_down, R.drawable.main_function_pda));

    }

    @Override
    protected void setUpView() {
        super.setUpView();
        initData();
        leftMenuList.setAdapter(mAdapter);
        leftMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectPosition(position);
                Bundle b = new Bundle();
                switch (position) {
                    case 0:
                        showHomeFragment();
                        break;
                    case 1:
                        b.putString(CatalogFragment.ExtraPATH, FileUtil.getExternalDir().getPath() + "/企业简介");
                        showFragment(CatalogFragment.class, b);
                        break;
                    case 2:
                        if (mFileCombineFragment == null) {
                            mFileCombineFragment = new FileCombineFragment();
                        }
                        FragmentTransaction ft = getSupportFragmentManager()
                                .beginTransaction();
                        ft.replace(R.id.detail_layout, mFileCombineFragment, null);
                        ft.commitAllowingStateLoss();
                        break;
                    case 3:
                        showFragment(GanWeiFragment.class, null);
                        break;
                    case 4:
                        b.putString(CatalogFragment.ExtraPATH, FileUtil.getExternalDir().getPath() + "/作业标准");
                        showFragment(CatalogFragment.class, b);
                        break;
                    case 5:
                        showFragment(ToolsFragment.class, null);
                        break;
                }
            }
        });
        showHomeFragment();
        hasNew();
    }

    private void hasNew() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void showFragment(Class<?> clss, Bundle b) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.detail_layout, Fragment.instantiate(this, clss.getName(), b));

        ft.commitAllowingStateLoss();
    }

    private void showHomeFragment() {
        if (homeFragemnt == null) {
            homeFragemnt = new HomeFragemnt();
        }
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.detail_layout, homeFragemnt, null);
        ft.commitAllowingStateLoss();
    }
    class ListAdapter extends BaseAdapter {
        private int selectPosition = 0;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Model getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setSelectPosition(int position) {
            selectPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_menu, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.menu_txt);
            tv.setText(getItem(position).name);
            if (position == selectPosition) {
                convertView.findViewById(R.id.iv_arrow).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.iv_item_icon).setBackgroundResource(getItem(position).selectedImg);
            } else {
                convertView.findViewById(R.id.iv_arrow).setVisibility(View.GONE);
                convertView.findViewById(R.id.iv_item_icon).setBackgroundResource(getItem(position).unSelectedImg);
            }
            return convertView;
        }
    }
}
