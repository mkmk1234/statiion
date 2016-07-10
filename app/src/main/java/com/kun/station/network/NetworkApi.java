package com.kun.station.network;

import android.content.Context;

import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.kun.station.MyApplication;
import com.kun.station.model.DeviceModel;
import com.kun.station.model.DirectoryModel;
import com.kun.station.model.FileModel;
import com.kun.station.model.HomeBannerModel;
import com.kun.station.model.NoticeModel;
import com.kun.station.response.MenuItemResponse;
import com.kun.station.util.PreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkApi {
    public static final String BASEURL = "http://114.215.182.202:8080/core-web/api";

    public static void bindDevice(String erialNumber, String equipmentNumber, Response.Listener<DeviceModel> listener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put("erialNumber", erialNumber);
        params.put("equipmentNumber", equipmentNumber);
        String url = BASEURL + "/equipment/bindDevice";
        MyApplication.getInstance().getNetworkManager().getPostResultClass(url, params, DeviceModel.class, listener, errorListener);
    }

    public static void getMenuInfo(Response.Listener<MenuItemResponse> listener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        String url = BASEURL + "/file/getMenuInfo";
        MyApplication.getInstance().getNetworkManager().getResultClass(url, params, MenuItemResponse.class, listener, errorListener);
    }

    public static void getHomeBanner(Response.Listener<ArrayList<HomeBannerModel>> listener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        String url = BASEURL + "/index/getHomeBanner";
        MyApplication.getInstance().getNetworkManager().getResultClass(url, params, new TypeToken<ArrayList<HomeBannerModel>>() {
        }.getType(), listener, errorListener);
    }

    public static void getHomeNotice(Response.Listener<ArrayList<NoticeModel>> listener, Response.ErrorListener errorListener, Context context) {
        Map<String, String> params = new HashMap<>();
        String url = BASEURL + "/index/getHomeNotice";
        params.put("lastTime", PreferencesUtils.getString(context, "noticeLastTime", ""));
        MyApplication.getInstance().getNetworkManager().getResultClass(url, params, new TypeToken<ArrayList<NoticeModel>>() {
        }.getType(), listener, errorListener);
    }

    public static void getFileInfo(Response.Listener<ArrayList<FileModel>> listener, Response.ErrorListener errorListener, Context context) {
        Map<String, String> params = new HashMap<>();
        String url = BASEURL + "/file/getFileInfo";
        params.put("lastTime", PreferencesUtils.getString(context, "fileLastTime", ""));
        MyApplication.getInstance().getNetworkManager().getResultClass(url, params, new TypeToken<ArrayList<FileModel>>() {
        }.getType(), listener, errorListener);
    }

    public static void getDirInfo(Response.Listener<ArrayList<DirectoryModel>> listener, Response.ErrorListener errorListener, Context context) {
        Map<String, String> params = new HashMap<>();
        String url = BASEURL + "/file/getDirInfo";
        params.put("lastTime", PreferencesUtils.getString(context, "dirLastTime", ""));
        MyApplication.getInstance().getNetworkManager().getResultClass(url, params, new TypeToken<ArrayList<DirectoryModel>>() {
        }.getType(), listener, errorListener);
    }

    public static void changeCollection(String fileId, String status, Response.Listener<DeviceModel> listener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        String url = BASEURL + "/file/changeCollection";
        params.put("fileId", fileId);
        params.put("status", status);
        MyApplication.getInstance().getNetworkManager().getResultClass(url, params, new TypeToken<ArrayList<DirectoryModel>>() {
        }.getType(), listener, errorListener);
    }

    public static void getCollectionInfo(Response.Listener<ArrayList<FileModel>> listener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        String url = BASEURL + "/file/getCollection";
        MyApplication.getInstance().getNetworkManager().getResultClass(url, params, new TypeToken<ArrayList<FileModel>>() {
        }.getType(), listener, errorListener);
    }

}
