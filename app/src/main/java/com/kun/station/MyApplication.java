package com.kun.station;

import android.app.Application;

import com.google.gson.Gson;
import com.kun.station.network.NetworkManager;

/**
 * Created by admin on 2016/6/3.
 */
public class MyApplication extends Application{
    private static MyApplication mInstance;
    private NetworkManager networkManager;
    public static Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mGson = new Gson();
    }

    public static MyApplication getInstance(){
        return mInstance;
    }
    

    public NetworkManager getNetworkManager() {
        if (networkManager == null) {
            networkManager = NetworkManager.getInstance(this);
        }
        return networkManager;
    }
}
