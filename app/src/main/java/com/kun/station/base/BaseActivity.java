package com.kun.station.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kun.station.util.Log;

import butterknife.ButterKnife;

/**
 * Created by kun on 16/6/3.
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!handIntentArgs()) {
            Log.toast(this, "参数错误");
            finish();
            return;
        }
        onSetContentView();
        ButterKnife.bind(this);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setUpView();
    }

    protected abstract void onSetContentView();

    protected void setUpView() {

    }

    protected boolean handIntentArgs() {
        return true;
    }


    // ====get parameters
    public int getIntParam(String name, int defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Integer.parseInt(val);
            }
        } catch (Exception e) {
        }

        return i.getIntExtra(name, defaultValue);
    }

    public int getIntParam(String name) {
        return getIntParam(name, 0);
    }

    public String getStringParam(String name) {
        return getStringParam(name, null);
    }

    public String getStringParam(String name, String defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                if (val != null)
                    return val;
            }
        } catch (Exception e) {
        }
        if (i.getExtras() != null && i.getExtras().containsKey(name)) {
            return i.getStringExtra(name);
        }
        return defaultValue;
    }

    public boolean getBooleanParam(String name, boolean defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                if (val != null)
                    return "true".equalsIgnoreCase(val) || "1".equals(val);
            }
        } catch (Exception e) {
        }
        return getIntent().getBooleanExtra(name, defaultValue);
    }

    public boolean getBooleanParam(String name) {
        return getBooleanParam(name, false);
    }

    public double getDoubleParam(String name, double defaultValue) {
        Intent i = getIntent();
        try {
            Uri uri = i.getData();
            if (uri != null) {
                String val = uri.getQueryParameter(name);
                return Double.parseDouble(val);
            }
        } catch (Exception e) {
        }

        return i.getDoubleExtra(name, defaultValue);
    }

    public double getDoubleParam(String name) {
        return getDoubleParam(name, 0);
    }

    public <T> T getSerializableParam(String name, Class<T> cls) {
        return getSerializableParam(name, null, cls);
    }

    public <T> T getSerializableParam(String name, T defaultValue, Class<T> cls) {
        Intent i = getIntent();
        if (i.getExtras() != null && i.getExtras().containsKey(name)) {
            return (T) i.getSerializableExtra(name);
        }
        if (defaultValue == null) {
            try {
                return cls.newInstance();
            } catch (Exception e) {
            }
        }
        return defaultValue;
    }


    // == show toast

    /**
     * 防止多次连续调用
     *
     * @param msg
     * @author dfqin
     */
    Toast toast;

    public void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public void showToast(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, duration);
        } else {
            toast.setText(msg);
            toast.setDuration(duration);
        }
        toast.show();
    }
}
