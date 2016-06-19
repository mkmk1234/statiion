package com.kun.station;

import com.kun.station.base.BaseActivity;
import com.kun.station.fragment.NewDownloadFileFragment;

/**
 * Created by admin on 2016/6/19.
 */
public class DownLoadFileActivity extends BaseActivity {
    private NewDownloadFileFragment downloadFileFragment;


    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_download_file);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        downloadFileFragment = new NewDownloadFileFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.ll_container, downloadFileFragment).commit();

    }
}
