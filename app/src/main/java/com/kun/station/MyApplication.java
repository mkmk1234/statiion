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
        File dir = FileUtil.getExternalDir();
        try {
            File dirFile0 = new File(dir, "企业简介");
            File dirFile1 = new File(dir, "规章资料");
            File dirFile2 = new File(dir, "作业标准");
            File dirFile3 = new File(dirFile0, "车站简介");
            File dirFile4 = new File(dirFile0, "企业精神");
            File dirFile5 = new File(dirFile0, "企业文化");
            File dirFile6 = new File(dirFile1, "技术规章");
            File dirFile7 = new File(dirFile1, "管理制度");
            File dirFile8 = new File(dirFile2, "岗位作业标准");
            File dirFile9 = new File(dirFile2, "岗位作业指导");
            dirFile0.mkdirs();
            dirFile1.mkdirs();
            dirFile2.mkdirs();
            dirFile3.mkdirs();
            dirFile4.mkdirs();
            dirFile5.mkdirs();
            dirFile6.mkdirs();
            dirFile7.mkdirs();
            dirFile8.mkdirs();
            dirFile9.mkdirs();
            new File(dirFile3, "车站简介001.doc").createNewFile();
            new File(dirFile3, "车站简介002.doc").createNewFile();
            new File(dirFile4, "企业精神001.pdf").createNewFile();
            new File(dirFile4, "企业精神002.pdf").createNewFile();
            new File(dirFile5, "企业文化001.doc").createNewFile();
            new File(dirFile5, "企业文化002.png").createNewFile();
            new File(dirFile6, "技术规章001.pdf").createNewFile();
            new File(dirFile6, "技术规章002.pdf").createNewFile();
            new File(dirFile7, "管理制度001.doc").createNewFile();
            new File(dirFile7, "管理制度002.png").createNewFile();
            new File(dirFile8, "岗位作业标准001.doc").createNewFile();
            new File(dirFile8, "岗位作业标准002.pdf").createNewFile();
            new File(dirFile9, "岗位作业指导001.doc").createNewFile();
            new File(dirFile9, "岗位作业指导002.pdf").createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
