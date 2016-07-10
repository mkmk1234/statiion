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
//            if (responseString.contains("subMenuList")) {
//                responseString = "{\"code\":200,\"data\":{\"dirList\":[{\"dirName\":\"车站状况\",\"dirPath\":\"/企业简介/车站状况\",\"id\":1,\"lastTime\":\"1468077987000\",\"status\":0},{\"dirName\":\"车站精神\",\"dirPath\":\"/企业简介/车站精神\",\"id\":2,\"lastTime\":\"1468078028000\",\"status\":0},{\"dirName\":\"安全文化\",\"dirPath\":\"/企业简介/安全文化\",\"id\":3,\"lastTime\":\"1468107882000\",\"status\":0},{\"dirName\":\"经营文化\",\"dirPath\":\"/企业简介/经营文化\",\"id\":4,\"lastTime\":\"1468108217000\",\"status\":0},{\"dirName\":\"路局、直属站车间管理制度\",\"dirPath\":\"/标准化车间/路局、直属站车间管理制度\",\"id\":10,\"lastTime\":\"1468108514000\",\"status\":0},{\"dirName\":\"车间班组文化理念\",\"dirPath\":\"/企业简介/车间班组文化理念\",\"id\":8,\"lastTime\":\"1468108514000\",\"status\":0},{\"dirName\":\"车间文化\",\"dirPath\":\"/企业简介/车间文化\",\"id\":6,\"lastTime\":\"1468108514000\",\"status\":0},{\"dirName\":\"上海铁路局员工守则\",\"dirPath\":\"/企业简介/上海铁路局员工守则\",\"id\":9,\"lastTime\":\"1468108514000\",\"status\":0},{\"dirName\":\"班组文化基本原则\",\"dirPath\":\"/企业简介/班组文化基本原则\",\"id\":7,\"lastTime\":\"1468108514000\",\"status\":0},{\"dirName\":\"廉政文化\",\"dirPath\":\"/企业简介/廉政文化\",\"id\":5,\"lastTime\":\"1468108514000\",\"status\":0},{\"dirName\":\"班组概况\",\"dirPath\":\"/标准化车间/班组概况\",\"id\":11,\"lastTime\":\"1468108609000\",\"status\":0},{\"dirName\":\"路局指导意见\",\"dirPath\":\"/标准化车间/路局、直属站车间管理制度/路局指导意见\",\"id\":12,\"lastTime\":\"1468108700000\",\"status\":0},{\"dirName\":\"专业处室实施办法\",\"dirPath\":\"/标准化车间/路局、直属站车间管理制度/专业处室实施办法\",\"id\":13,\"lastTime\":\"1468108782000\",\"status\":0}],\"menuList\":[{\"icon\":\"\",\"id\":1,\"subMenuList\":[],\"title\":\"系统首页\",\"type\":0},{\"icon\":\"\",\"id\":5,\"subMenuList\":[{\"icon\":\"\",\"id\":2,\"title\":\"车站状况\",\"type\":102},{\"icon\":\"\",\"id\":14,\"title\":\"上海铁路局员工守则\",\"type\":102},{\"icon\":\"\",\"id\":7,\"title\":\"车站精神\",\"type\":102},{\"icon\":\"\",\"id\":10,\"title\":\"廉政文化\",\"type\":102},{\"icon\":\"\",\"id\":12,\"title\":\"班组文化基本原则\",\"type\":102},{\"icon\":\"\",\"id\":8,\"title\":\"安全文化\",\"type\":102},{\"icon\":\"\",\"id\":11,\"title\":\"车间文化\",\"type\":102},{\"icon\":\"\",\"id\":13,\"teamConcept\":\"555555cccccc\",\"title\":\"车间班组文化理念\",\"type\":101},{\"icon\":\"\",\"id\":9,\"title\":\"经营文化\",\"type\":102}],\"title\":\"企业简介\",\"type\":1},{\"icon\":\"\",\"id\":15,\"subMenuList\":[],\"title\":\"标准化车间\",\"type\":2},{\"icon\":\"\",\"id\":16,\"subMenuList\":[],\"title\":\"职工教育\",\"type\":2},{\"icon\":\"\",\"id\":17,\"subMenuList\":[],\"title\":\"技术管理\",\"type\":2},{\"icon\":\"\",\"id\":18,\"subMenuList\":[],\"title\":\"安全管理\",\"type\":2},{\"icon\":\"\",\"id\":19,\"subMenuList\":[],\"title\":\"应急处置\",\"type\":2},{\"icon\":\"\",\"id\":20,\"subMenuList\":[],\"title\":\"综合资料\",\"type\":2}]},\"message\":\"加载成功\"}";
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
