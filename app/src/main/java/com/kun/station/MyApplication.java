package com.kun.station;

import android.app.Application;

import com.google.gson.Gson;
import com.kun.station.db.DbManager;
import com.kun.station.util.FileUtil;

import java.io.File;

/**
 * Created by admin on 2016/6/3.
 */
public class MyApplication extends Application{
    private static MyApplication mInstance;
    private DbManager mDbManager;
    public static Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mGson = new Gson();
        addTestFile();
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    public DbManager getDbManager(){
        if (mDbManager == null){
            mDbManager = new DbManager(mInstance);
        }
        return mDbManager;
    }

    private void addTestFile(){
        String[] data = {"车站状况", "车站精神", "安全文化", "经营文化", "廉政文化", "车间文化", "班组文化基本原则", "车间班组文化理念", "上海铁路局员工守则"};

        File dir = FileUtil.getExternalDir();
        try {
            File dirFile0 = new File(dir, "企业简介");
            for (int i = 0; i < data.length; i++) {
                File dirFile1 = new File(dirFile0, data[i]);
                dirFile1.mkdirs();
                new File(dirFile1, data[i] + ".doc").createNewFile();
                new File(dirFile1, data[i] + ".jpg").createNewFile();
                new File(dirFile1, data[i] + ".pdf").createNewFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
