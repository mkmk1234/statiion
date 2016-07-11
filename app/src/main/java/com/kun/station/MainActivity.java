package com.kun.station;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kun.station.base.BaseActivity;
import com.kun.station.db.DbManager;
import com.kun.station.fragment.CatalogFragment;
import com.kun.station.fragment.FileCombineFragment;
import com.kun.station.fragment.GanWeiFragment;
import com.kun.station.fragment.HomeFragemnt;
import com.kun.station.model.MenuItemModel;
import com.kun.station.model.MenuModel;
import com.kun.station.model.SubMenuModel;
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
    @Bind(R.id.txt_time)
    TextView txtTime;
    @Bind(R.id.txt_wifi_state)
    TextView txtWifiState;
    WifiStateChangedReceiver wifiStateChangedReceiver;
    ScreenBroadcastReceiver screenBroadcastReceiver;
    Fragment currentFragment;

    private ListAdapter mAdapter;
    private FileCombineFragment mFileCombineFragment;
    private HomeFragemnt homeFragemnt;
    private List<MenuItemModel> menuItemModelList;
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
        List<MenuModel> list = DbManager.getInstace(MainActivity.this).getMenu();
        menuItemModelList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MenuModel menuModel = list.get(i);
            List<SubMenuModel> subMenuModelList = new ArrayList<>();
            if (menuModel.getType() == 1) {
                subMenuModelList = DbManager.getInstace(MainActivity.this).getSubMenu(menuModel.getId());
            }
            MenuItemModel menuItemModel = new MenuItemModel(menuModel, subMenuModelList);
            menuItemModelList.add(menuItemModel);
        }
    }


    @Override
    protected void setUpView() {
        super.setUpView();
        initData();
        regReceiver();
        registerScreenActionReceiver();
        leftMenuList.setAdapter(mAdapter);
        leftMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectPosition(position);
                Bundle b = new Bundle();
                MenuItemModel menuItemModel = menuItemModelList.get(position);
                switch (menuItemModel.getType()) {
                    case 0:
                        showHomeFragment();
                        currentFragment = homeFragemnt;
                        break;
                    case 2:
                        mFileCombineFragment = new FileCombineFragment();
                        File rootFile = new File(FileUtil.getExternalDir(), menuItemModel.getTitle());
                        Bundle mBundle = new Bundle();
                        mBundle.putString(CatalogFragment.ExtraPATH, rootFile.getAbsolutePath());
                        mFileCombineFragment.setArguments(mBundle);
                        FragmentTransaction ft = getSupportFragmentManager()
                                .beginTransaction();
                        ft.replace(R.id.detail_layout, mFileCombineFragment, null);
                        ft.commitAllowingStateLoss();
                        currentFragment = mFileCombineFragment;
                        break;
                    case 1:
                        b.putInt("menuId", menuItemModel.getId());
                        b.putString("menuTitle", menuItemModel.getTitle());
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

    private void registerScreenActionReceiver() {
        screenBroadcastReceiver = new ScreenBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiStateChangedReceiver);
        unregisterReceiver(screenBroadcastReceiver);
        Log.i("sss", "onDestroy");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("sss", "onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("sss", "onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("sss", "onRestoreInstanceState");
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
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    class ListAdapter extends BaseAdapter {
        private int selectPosition = 0;

        @Override
        public int getCount() {
            return menuItemModelList == null ? 0 : menuItemModelList.size();
        }

        @Override
        public MenuItemModel getItem(int position) {
            return menuItemModelList.get(position);
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
            tv.setText(getItem(position).getTitle());
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



