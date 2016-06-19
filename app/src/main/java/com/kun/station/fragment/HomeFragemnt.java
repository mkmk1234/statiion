package com.kun.station.fragment;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kun.station.DownLoadFileActivity;
import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.util.PreferencesUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kun on 16/5/25.
 */
public class HomeFragemnt extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.top_menu)
    RadioGroup topMenu;
    @Bind(R.id.txt_deviceID)
    TextView txtDeviceID;
    @Bind(R.id.btn_wifi)
    ImageView btnWifi;
    String deviceID;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        getDeviceID();
        showWifiBtn();
        txtDeviceID.setText("设备： " + deviceID);
        topMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_fst:
                        Bundle b1 = new Bundle();
                        showFragment(HomePageFragment.class, b1);
                        break;
                    case R.id.rb_sec:
                        showFragment(NoticeFragment.class, null);
                        break;
                    case R.id.rb_thd:
                        showFragment(NewFragment.class, null);

                        break;
                }
            }
        });
        Bundle b1 = new Bundle();
        showFragment(HomePageFragment.class, b1);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void hasNew() {
        topMenu.check(R.id.rb_thd);
    }

    private void getDeviceID() {
        deviceID = PreferencesUtils.getString(getActivity(), "deviceID");
    }

    private void showWifiBtn() {
        if (getWifiState() == WifiManager.WIFI_STATE_DISABLED || getWifiState() == WifiManager.WIFI_STATE_DISABLING) {
            btnWifi.setImageResource(R.drawable.img_close_3g);

        } else if (getWifiState() == WifiManager.WIFI_STATE_ENABLED || getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
            btnWifi.setImageResource(R.drawable.img_open_3g);
        }
    }
    private int getWifiState() {
        WifiManager wifiManger = (WifiManager) getActivity().getSystemService(Service.WIFI_SERVICE);
        return wifiManger.getWifiState();
    }

    private void openWifi() {
        WifiManager wifiManger = (WifiManager) getActivity().getSystemService(Service.WIFI_SERVICE);
        wifiManger.setWifiEnabled(true);
    }

    private void closeWifi() {
        WifiManager wifiManger = (WifiManager) getActivity().getSystemService(Service.WIFI_SERVICE);
        wifiManger.setWifiEnabled(false);
    }

    private void showFragment(Class<?> clss, Bundle b) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.home_container, Fragment.instantiate(getActivity(), clss.getName(), b));

        ft.commitAllowingStateLoss();
    }

    private void showDialog() {

    }

    @OnClick({R.id.btn_wifi, R.id.tv_download})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wifi:
                if (getWifiState() == WifiManager.WIFI_STATE_ENABLING || getWifiState() == WifiManager.WIFI_STATE_DISABLING || getWifiState() == WifiManager.WIFI_STATE_UNKNOWN) {
                    return;
                }
                if (getWifiState() == WifiManager.WIFI_STATE_DISABLED) {
                    openWifi();
                    btnWifi.setImageResource(R.drawable.img_open_3g);

                } else {
                    closeWifi();
                    btnWifi.setImageResource(R.drawable.img_close_3g);
                }
                break;

            case R.id.tv_download:
                startActivity(new Intent(getActivity(), DownLoadFileActivity.class));
                break;
        }
    }

}
