package com.kun.station.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by kun on 16/6/3.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpView();
    }

    protected void showFragment(Class<?> clss, Bundle b, int container) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager()
                .beginTransaction();
        ft.replace(container, Fragment.instantiate(getActivity(), clss.getName(), b));

        ft.commitAllowingStateLoss();
    }
    protected void setUpView() {

    }

    protected void handIntentArgs() {
    }
    // ====get parameters

    public int getIntParam(String name, int defaultValue) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
                Integer val = bundle.getInt(name);
                return val;
            } catch (Exception e) {
            }
            return bundle.getInt(name, defaultValue);
        }
        return defaultValue;
    }

    public int getIntParam(String name) {
        return getIntParam(name, 0);
    }

    public boolean getBooleanParam(String name, boolean defaultValue) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(name)) {
            String val = bundle.getString(name);
            if (val != null)
                return "true".equalsIgnoreCase(val) || "1".equals(val);
            return bundle.getBoolean(name);
        }
        return defaultValue;
    }

    public void showLoadingDialog() {
        showLoadingDialog(null);
    }

    public void showLoadingDialog(String message) {
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).showLoadingDialog(message);
        }
    }

    public void hideLoadingDialog() {
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).hideLoadingDialog();
        }
    }

    public boolean getBooleanParam(String name) {
        return getBooleanParam(name, false);
    }

    public String getStringParam(String name, String defaultValue) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(name)) {
            return bundle.getString(name);
        }
        return defaultValue;
    }

    public String getStringParam(String name) {
        return getStringParam(name, null);
    }

    public double getDoubleParam(String name, double defaultValue) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
                String val = bundle.getString(name);
                return Double.parseDouble(val);
            } catch (Exception e) {
            }
            return bundle.getDouble(name, defaultValue);
        }
        return defaultValue;
    }

    public double getDoubleParam(String name) {
        return getDoubleParam(name, 0);
    }

    public <T> T getSerializableParam(String name, Class<T> cls) {
        return getSerializableParam(name, null, cls);
    }

    public <T> T getSerializableParam(String name, T defaultValue, Class<T> cls) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(name)) {
            return (T) bundle.getSerializable(name);
        }
        if (defaultValue == null) {
            try {
                return cls.newInstance();
            } catch (Exception e) {
            }
        }
        return defaultValue;
    }

}
