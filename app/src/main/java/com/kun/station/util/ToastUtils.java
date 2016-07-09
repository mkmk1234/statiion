package com.kun.station.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.kun.station.MyApplication;


/**
 * Toast工具
 *
 * @author kun
 */
public class ToastUtils {

    public static final void showToast(int resId) {
        Toast.makeText(MyApplication.getInstance(), resId, Toast.LENGTH_SHORT).show();
    }

    public static final void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
        }
    }

}
