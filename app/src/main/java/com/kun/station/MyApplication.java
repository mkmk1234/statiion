package com.kun.station;

import android.app.Application;

import com.kun.station.db.DbManager;
import com.kun.station.db.DbOpenHelper;

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
}
