package com.kun.station.fragment;

import android.app.Service;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kun.station.MyApplication;
import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.db.DbManager;
import com.kun.station.model.FileModel;
import com.kun.station.model.FileShowModel;
import com.kun.station.util.FileUtil;
import com.kun.station.util.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.iv_wifi)
    ImageView ivWifi;
    String deviceID;
    HomePageFragment homePageFragment;
    NoticeFragment noticeFragment;
    NewDownloadFileFragment newDownloadFileFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        getDeviceID();
        showWifiBtn();
        txtDeviceID.setText("设备： " + deviceID);
        initFragment();
        topMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_fst:
                        showFragment(0);
                        break;
                    case R.id.rb_sec:
                        showFragment(1);
                        break;
                    case R.id.rb_thd:
                        showFragment(2);
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initFragment() {
        homePageFragment = (HomePageFragment) Fragment.instantiate(getActivity(), HomePageFragment.class.getName(), null);
        noticeFragment = (NoticeFragment) Fragment.instantiate(getActivity(), NoticeFragment.class.getName(), null);
        newDownloadFileFragment = (NewDownloadFileFragment) Fragment.instantiate(getActivity(), NewDownloadFileFragment.class.getName(), null);
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.add(R.id.home_container, homePageFragment, "homepage");
        ft.add(R.id.home_container, noticeFragment, "notice");
        ft.add(R.id.home_container, newDownloadFileFragment, "download");
        ft.hide(newDownloadFileFragment);
        ft.hide(noticeFragment);
        ft.show(homePageFragment);
        ft.commit();

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

    private void showFragment(int flag) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        switch (flag) {
            case 0:
                ft.show(homePageFragment);
                ft.hide(noticeFragment);
                ft.hide(newDownloadFileFragment);
                break;
            case 1:
                ft.show(noticeFragment);
                ft.hide(homePageFragment);
                ft.hide(newDownloadFileFragment);
                break;
            case 2:
                ft.show(newDownloadFileFragment);
                ft.hide(noticeFragment);
                ft.hide(homePageFragment);
                break;
        }

        ft.commit();
    }


    private List<FileShowModel> getNewFileList() {
        List<FileShowModel> fileShowList = new ArrayList<>();
        DbManager dbManager = DbManager.getInstace(getContext());
        List<FileModel> list = MyApplication.mGson.fromJson(FileUtil.loadRawString(getContext(), R.raw.localdata_list), new TypeToken<ArrayList<FileModel>>() {
        }.getType());
        for (int i = 0; i < list.size(); i++) {
            FileModel fileModel = list.get(i);
            fileShowList.add(new FileShowModel(fileModel, 0, true, false, dbManager.isStore(fileModel.dirName, fileModel.fileName), false, ""));
        }
        for (int i = 0; i < fileShowList.size(); i++) {
            DbManager.getInstace(getContext()).insertFile(fileShowList.get(i));
        }
        return fileShowList;
    }

    private List<FileShowModel> getNoDownloadFileList() {
        return DbManager.getInstace(getContext()).getShowFiles();
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
                    ivWifi.setImageResource(R.drawable.ico_set_wifi);

                } else {
                    closeWifi();
                    btnWifi.setImageResource(R.drawable.img_close_3g);
                    ivWifi.setImageResource(R.drawable.ico_set_wifi_off);
                }
                break;

            case R.id.tv_download:
                List<FileShowModel> list = getNewFileList();
                list.addAll(getNoDownloadFileList());
                newDownloadFileFragment.update(list);
                topMenu.check(R.id.rb_thd);
                break;
        }
    }

}
