package com.kun.station.network;

import com.android.volley.Response;
import com.kun.station.MyApplication;
import com.kun.station.model.DeviceModel;

import java.util.HashMap;
import java.util.Map;

public class NetworkApi {

    public static void bindDevice(String erialNumber, String equipmentNumber, Response.Listener<DeviceModel> listener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put("erialNumber", erialNumber);
        params.put("equipmentNumber", equipmentNumber);
        String url = String.format("", "");
        MyApplication.getInstance().getNetworkManager().getPostResultClass(url, params, DeviceModel.class, listener, errorListener);
    }

    public static void getMenuInfo(Response.Listener<DeviceModel> listener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        String url = String.format("", "");
        MyApplication.getInstance().getNetworkManager().getResultClass(url, params, DeviceModel.class, listener, errorListener);
    }

    public static void getHomeBanner() {

    }

    public static void getHomeNotice() {

    }

    public static void getFileInfo() {

    }

    public static void getDirInfo() {

    }


}
