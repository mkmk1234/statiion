package com.kun.station.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kun.station.MyApplication;

import org.apache.http.client.HttpClient;

import java.lang.reflect.Type;
import java.util.Map;

//import com.siyanhui.mechat.Application;

public class NetworkManager {
    private static NetworkManager mManager;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Response.ErrorListener mDefaultErrorListener;

    private HttpClient mClient;

    public static NetworkManager getInstance(Context context){
        if(mManager == null){
            mManager = new NetworkManager(context);
        }
        return mManager;
    }

    private NetworkManager(final Context context){
        mRequestQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
        mDefaultErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }

    public void getResultClass(String url, Class objectClass, Response.Listener listener){
        this.getResultClass(url, null, objectClass, listener, mDefaultErrorListener);
    }

    public void getResultClass(String url, Class objectClass, Response.Listener listener,
                               Response.ErrorListener errorListener){
        this.getResultClass(url, null, objectClass, listener, errorListener);
    }

    public void getResultClass(String url, Map<String, String> params, Class objectClass,
                               Response.Listener listener){
        this.getResultClass(url, params, objectClass, listener, mDefaultErrorListener);
    }

    public void getResultClass(String url, Map<String, String> params, Type objectType,
                               Response.Listener listener, Response.ErrorListener errorListener){
        GsonRequest request = new GsonRequest(Request.Method.GET, url + "?" + addParams(params),
                null, objectType, listener, errorListener);
        mRequestQueue.add(request);
    }

    public void getResultClass(String url, Map<String, String> params, Class objectClass,
                               Response.Listener listener, Response.ErrorListener errorListener){
        GsonRequest request = new GsonRequest(Request.Method.GET, url + "?" + addParams(params),
                null, objectClass, listener, errorListener);
        mRequestQueue.add(request);
    }

    public void getPostResultClass(String url, Map<String, String> params, Class objectClass,
                                   Response.Listener listener, Response.ErrorListener errorListener){
        GsonRequest request = new GsonRequest(Request.Method.POST, url, params, objectClass, listener,
                errorListener == null ? mDefaultErrorListener : errorListener);
        mRequestQueue.add(request);
    }

    public void getResultString(String url, Response.Listener<String> mListener){
        this.getResultString(url, null, mListener);
    }

    public void getResultString(String url, Map<String, String> params, Response.Listener<String> mListener){
        this.getResultString(url, params, mListener, null);
    }

    public void getResultString(String url, Map<String, String> params,
                                Response.Listener<String> mListener, Response.ErrorListener errorListener){
        String finalUrl = url + "?" + addParams(params);
        StringRequest request = new StringParamsRequest(Request.Method.GET,
                finalUrl,null, mListener, errorListener == null ? mDefaultErrorListener : errorListener);
        mRequestQueue.add(request);
    }

    public void getPostResultString(String url, Response.Listener<String> mListener){
        this.getPostResultString(url, null, mListener);
    }

    public void getPostResultString(String url, Map<String, String> params, Response.Listener<String> mListener){
        StringRequest request = new StringParamsRequest(Request.Method.POST,url, params, mListener, mDefaultErrorListener);
        mRequestQueue.add(request);
    }

    private static String addParams(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        if(params != null){
            for(Map.Entry<String, String> item : params.entrySet()){
                builder.append(item.getKey()).append("=").append(item.getValue()).append("&");
            }
        }
        builder.append("erialId=").append(MyApplication.erialId);

        return builder.toString();
    }
}
