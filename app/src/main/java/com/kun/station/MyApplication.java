package com.kun.station;

import android.app.Application;

import com.kun.station.db.DbManager;
import com.kun.station.db.DbOpenHelper;
import com.kun.station.util.FileUtil;

import java.io.File;

/**
 * Created by admin on 2016/6/3.
 */
public class MyApplication extends Application{
    private static MyApplication mInstance;
    private DbManager mDbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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
            File dirFile = new File(dir, "人员文档");
            if (!dirFile.exists()){
                dirFile.mkdir();
            }
            File file = new File(dirFile, "人员001.png");
            if (!file.exists()){
                file.createNewFile();
                new File(dir, "人员002.doc").createNewFile();
                new File(dir, "人员003.pdf").createNewFile();
                new File(dir, "人员004.pdf").createNewFile();
                new File(dir, "人员005.doc").createNewFile();
                new File(dir, "人员006.png").createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
