package com.kun.station.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.kun.station.MyApplication;
import com.kun.station.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.zip.GZIPInputStream;

/**
 * Created by dcy123 on 2015/3/18.
 */
public class GsonRequest extends Request{
    private Gson mGson;
    private Map<String, String> mParams;
    private Class mClass;
    private Type mType;
    private Response.Listener mListener;

    public GsonRequest(int method, String url, Map<String, String> params, Class objectClass,
                       Response.Listener listener, Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mListener = listener;
        mParams = params;
        mClass = objectClass;
        mGson = new Gson();
    }

    public GsonRequest(int method, String url, Map<String, String> params, Type objectType,
                       Response.Listener listener, Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mListener = listener;
        mParams = params;
        mType = objectType;
        mGson = new Gson();
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString = new String(response.data);
//            if (responseString.contains("dirPath")) {
//                responseString = "{\"code\":200,\"data\":[{\"dirId\":12,\"dirName\":\"意见\",\"dirPath\":\"/标准化车间/路局、直属站车间管理制度/意见\",\"lastTime\":\"1468054700000\",\"status\":0},{\"dirId\":11,\"dirName\":\"班组概况\",\"dirPath\":\"/标准化车间/班组概况\",\"lastTime\":\"1468054609000\",\"status\":0},{\"dirId\":9,\"dirName\":\"上海铁路局员工守则\",\"dirPath\":\"/企业简介/上海铁路局员工守则\",\"lastTime\":\"1468054514000\",\"status\":0},{\"dirId\":7,\"dirName\":\"班组文化基本原则\",\"dirPath\":\"/企业简介/班组文化基本原则\",\"lastTime\":\"1468054514000\",\"status\":0},{\"dirId\":5,\"dirName\":\"廉政文化\",\"dirPath\":\"/企业简介/廉政文化\",\"lastTime\":\"1468054514000\",\"status\":0},{\"dirId\":10,\"dirName\":\"路局、直属站车间管理制度\",\"dirPath\":\"/标准化车间/路局、直属站车间管理制度\",\"lastTime\":\"1468054514000\",\"status\":0},{\"dirId\":8,\"dirName\":\"车间班组文化理念\",\"dirPath\":\"/企业简介/车间班组文化理念\",\"lastTime\":\"1468054514000\",\"status\":0},{\"dirId\":6,\"dirName\":\"车间文化\",\"dirPath\":\"/企业简介/车间文化\",\"lastTime\":\"1468054514000\",\"status\":0},{\"dirId\":4,\"dirName\":\"经营文化\",\"dirPath\":\"/企业简介/经营文化\",\"lastTime\":\"1468054217000\",\"status\":0},{\"dirId\":3,\"dirName\":\"安全文化\",\"dirPath\":\"/企业简介/安全文化\",\"lastTime\":\"1468053882000\",\"status\":0},{\"dirId\":2,\"dirName\":\"车站精神\",\"dirPath\":\"/企业简介/车站精神\",\"lastTime\":\"1468024028000\",\"status\":0},{\"dirId\":1,\"dirName\":\"车站状况\",\"dirPath\":\"/企业简介/车站状况\",\"lastTime\":\"1468023987000\",\"status\":0}],\"message\":\"加载成功\"}";
//            }
            Log.i("sss", responseString);
            JSONObject resultObject;
            if(response.headers.get("Content-Encoding") != null &&
                    response.headers.get("Content-Encoding").indexOf("gzip") > -1){
                String jsonString = getGZipString(new ByteArrayInputStream(response.data));
                resultObject = new JSONObject(jsonString);
                try {
                    VolleyLog.d(jsonString);
                } catch (MissingFormatArgumentException e) {
                }
            } else {
                resultObject = new JSONObject(responseString);
                try {
                    VolleyLog.d(responseString);
                } catch (MissingFormatArgumentException e) {
                }
            }
            if(resultObject.getInt("code") == 200){
                if (resultObject.has("data")) {
                    return Response.success(mGson.fromJson(resultObject.getString("data"), mClass == null ? mType : mClass),
                            HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                }
            } else {
                return Response.error(new VolleyError(resultObject.getString("message"), resultObject.getInt("code")));
            }
        } catch (Exception exception){
            return Response.error(new ParseError(exception));
        }
    }

    private String getGZipString(InputStream inputStream){
        GZIPInputStream gzin = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        try {
            gzin = new GZIPInputStream(inputStream);
            isr = new InputStreamReader(gzin);
            br = new BufferedReader(isr);
            String lineString;
            while ((lineString = br.readLine()) != null){
                builder.append(lineString);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(gzin != null){
                    gzin.close();
                }
                if(isr != null){
                    isr.close();
                }
                if(br != null){
                    br.close();
                }
            } catch (Exception e){
            }
        }
        return builder.toString();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if(mParams == null){
            mParams = new HashMap<>();
        }
        mParams.put("erialId", MyApplication.erialId);

        return mParams;
    }

    @Override
    protected void deliverResponse(Object response) {
        mListener.onResponse(response);
    }
}
