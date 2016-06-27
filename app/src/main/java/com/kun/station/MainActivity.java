package com.kun.station;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.kun.station.base.BaseActivity;
import com.kun.station.fragment.CatalogFragment;
import com.kun.station.fragment.FileCombineFragment;
import com.kun.station.fragment.GanWeiFragment;
import com.kun.station.fragment.HomeFragemnt;
import com.kun.station.model.FileModel;
import com.kun.station.response.MenuItemResponse;
import com.kun.station.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseActivity {

    @Bind(R.id.left_menu_list)
    ListView leftMenuList;
    @Bind(R.id.detail_layout)
    FrameLayout detailLayout;
    List<MenuItemResponse> list;
    @Bind(R.id.txt_time)
    TextView txtTime;
    @Bind(R.id.txt_wifi_state)
    TextView txtWifiState;
    WifiStateChangedReceiver wifiStateChangedReceiver;
    Fragment currentFragment;

    private ListAdapter mAdapter;
    private FileCombineFragment mFileCombineFragment;
    private HomeFragemnt homeFragemnt;
    int[] selelctedImg = {R.drawable.main_function_home_down, R.drawable.main_function_enterprise_down,
            R.drawable.main_function_book_down, R.drawable.main_function_post_constrution_down
            , R.drawable.main_function_standard_down, R.drawable.main_function_pda_down, R.drawable.main_function_pda_down, R.drawable.main_function_pda_down};
    int[] unSelectedImg = {R.drawable.main_function_home, R.drawable.main_function_enterprise,
            R.drawable.main_function_book, R.drawable.main_function_post_constrution
            , R.drawable.main_function_standard, R.drawable.main_function_pda, R.drawable.main_function_pda, R.drawable.main_function_pda};


    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_main);
    }

    private void initData() {
        mAdapter = new ListAdapter();
        list = MyApplication.mGson.fromJson(FileUtil.loadRawString(this, R.raw.localdata), new TypeToken<ArrayList<MenuItemResponse>>() {
        }.getType());
        createDir();
//        list.add(new Model("系统首页", R.drawable.main_function_home_down, R.drawable.main_function_home));
//        list.add(new Model("企业简介", R.drawable.main_function_enterprise_down, R.drawable.main_function_enterprise));
//        list.add(new Model("规章资料", R.drawable.main_function_book_down, R.drawable.main_function_book));
//        list.add(new Model("岗位建设", R.drawable.main_function_post_constrution_down, R.drawable.main_function_post_constrution));
//        list.add(new Model("作业标准", R.drawable.main_function_standard_down, R.drawable.main_function_standard));
//        list.add(new Model("运输生产", R.drawable.main_function_pda_down, R.drawable.main_function_pda));
//        list.add(new Model("运输生产", R.drawable.main_function_pda_down, R.drawable.main_function_pda));
    }

    private void createDir() {
        if (list == null || list.size() == 0) {
            return;
        }
        File stationDir = FileUtil.getExternalDir();
        File dirFile;
        for (MenuItemResponse itemResponse : list) {
            if (itemResponse.type == 1 && itemResponse.fileList.size() > 0) {
                for (FileModel itemFile : itemResponse.fileList) {
                    dirFile = new File(stationDir, itemFile.dirName);
                    if (!dirFile.exists()) {
                        dirFile.mkdirs();
                    }
//                    try {
//                        new File(dirFile, itemFile.fileName).createNewFile();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
                }
            }
        }
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        initData();
        regReceiver();
        leftMenuList.setAdapter(mAdapter);
        leftMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectPosition(position);
                Bundle b = new Bundle();
                MenuItemResponse itemResponse = list.get(position);
                switch (itemResponse.type) {
                    case 0:
                        showHomeFragment();
                        currentFragment = homeFragemnt;
                        break;
                    case 1:
                        mFileCombineFragment = new FileCombineFragment();
                        File rootFile = new File(FileUtil.getExternalDir(), itemResponse.title);
                        Bundle mBundle = new Bundle();
                        mBundle.putString(CatalogFragment.ExtraPATH, rootFile.getAbsolutePath());
                        mFileCombineFragment.setArguments(mBundle);
                        FragmentTransaction ft = getSupportFragmentManager()
                                .beginTransaction();
                        ft.replace(R.id.detail_layout, mFileCombineFragment, null);
                        ft.commitAllowingStateLoss();
                        currentFragment = mFileCombineFragment;
                        break;
                    case 2:
                        showFragment(GanWeiFragment.class, b);
                        break;
                }

            }
        });
        showHomeFragment();
    }



    private void showFragment(Class<?> clss, Bundle b) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        Fragment fragment = Fragment.instantiate(this, clss.getName(), b);
        ft.replace(R.id.detail_layout, fragment);

        ft.commitAllowingStateLoss();
        currentFragment = fragment;
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

    private void regReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        wifiStateChangedReceiver = new WifiStateChangedReceiver();
        registerReceiver(wifiStateChangedReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiStateChangedReceiver);
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != null && currentFragment instanceof FileCombineFragment) {
            Fragment fragment = ((FileCombineFragment) currentFragment).getCurrentFragment();
            if (fragment != null && fragment instanceof CatalogFragment)
                ((CatalogFragment) fragment).back();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_HOME == keyCode) {
            System.out.println("HOME has been pressed yet ...");
            // android.os.Process.killProcess(android.os.Process.myPid());
            Toast.makeText(getApplicationContext(), "HOME 键已被禁用...",
                    Toast.LENGTH_LONG).show();
        }
        return true;
    }

    class ListAdapter extends BaseAdapter {
        private int selectPosition = 0;

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public MenuItemResponse getItem(int position) {
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
            tv.setText(getItem(position).title);
            convertView.findViewById(R.id.iv_item_icon).setBackgroundResource(R.drawable.main_function_home_down);
            if (position == selectPosition) {
                convertView.findViewById(R.id.iv_arrow).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.iv_item_icon).setBackgroundResource(selelctedImg[position]);
            } else {
                convertView.findViewById(R.id.iv_arrow).setVisibility(View.GONE);
                convertView.findViewById(R.id.iv_item_icon).setBackgroundResource(unSelectedImg[position]);
            }
            return convertView;
        }
    }

    class WifiStateChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 这个监听wifi的打开与关闭，与wifi的连接无关
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        txtWifiState.setText("wifi状态：已关闭");
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        txtWifiState.setText("wifi状态：关闭中");
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        txtWifiState.setText("wifi状态：已开启");
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        txtWifiState.setText("wifi状态：开启中");
                        break;
                    //
                }
            }

        }
    }
}



