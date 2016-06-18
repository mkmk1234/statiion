package com.kun.station;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.kun.station.base.BaseActivity;
import com.kun.station.fragment.DownloadFileFragment;

/**
 * Created by admin on 2016/6/19.
 */
public class DownLoadFileActivity extends FragmentActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        getSupportFragmentManager().beginTransaction().add(R.id.ll_container, new DownloadFileFragment()).commit();
    }
}
