package com.kun.station.util;

/*
 * Copyright (C) 2010 Michael Pardo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


public abstract class AndroidUtils {

    private static final String TAG = AndroidUtils.class.getSimpleName();

    private AndroidUtils() {
    }

    public static String getAppName(Context context, String packageName) {
        String applicationName;

        if (TextUtils.isEmpty(packageName)) {
            packageName = context.getPackageName();
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            applicationName = context.getString(packageInfo.applicationInfo.labelRes);
        } catch (Exception e) {
            Log.w("Failed to get version number.", e.getLocalizedMessage());
            applicationName = "";
        }

        return applicationName;
    }

    public static int getSdkVersion() {
        try {
            return Build.VERSION.class.getField("SDK_INT").getInt(null);
        } catch (Exception e) {
            return 3;
        }
    }

    public static boolean isEmulator() {
        return Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
    }

    /**
     * 版本号比较
     *
     * @param a
     * @param b
     * @return if a = b return 0; a > b return 1;a < b return -1
     */
    public static int compareVersionName(String a, String b) {
        if (TextUtils.isEmpty(a) || TextUtils.isEmpty(b)) {
            return 0;
        }
        if (a.equals(b)) {
            return 0;
        }
        int[] target = versionNames(a);
        int[] current = versionNames(b);
        int len = Math.min(current.length, target.length);
        for (int i = 0; i < len; i++) {
            if (current[i] == target[i]) {
                continue;
            }
            return target[i] > current[i] ? 1 : -1;
        }
        if (target.length == current.length) {
            return 0;
        }
        boolean isTargetLonger = target.length > current.length;
        int[] tmp = isTargetLonger ? target : current;
        for (int i = len; i < tmp.length; i++) {
            if (tmp[len] > 0) {
                return isTargetLonger ? 1 : -1;
            }
        }
        return 0;
    }

    private static int[] versionNames(String versionName) {
        if (TextUtils.isEmpty(versionName)) {
            return new int[0];
        }
        String[] names = versionName.split("\\.");
        int[] intName = new int[names.length];
        for (int i = 0; i < names.length; i++) {
            intName[i] = Integer.valueOf(names[i].trim());
        }
        return intName;
    }

    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    public static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        if ((preferExternal)
                && (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
                && (hasExternalStoragePermission(context))) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            Log.w(TAG, String.format("Can't define system cache directory! '%s' will be used.",
                    new Object[]{cacheDirPath}));
            appCacheDir = new File(cacheDirPath);
        }
        if (appCacheDir != null && !appCacheDir.exists()) {
            appCacheDir.mkdirs();
        }
        return appCacheDir;
    }

    public static boolean hasExternalStorage(Context context) {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static boolean hasExternalStoragePermission(Context context) {
        return hasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean hasPermission(Context context, String permission) {
        int perm = context.checkCallingOrSelfPermission(permission);
        return (perm == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasFeature(Context context, String feature) {
        return context.getPackageManager().hasSystemFeature(feature);
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"),
                "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!(appCacheDir.exists()) || !appCacheDir.isDirectory()) {
            if (!(appCacheDir.mkdirs())) {
                Log.w("Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i(TAG, String.format(
                        "Can't create \".nomedia\" file in application external cache directory",
                        new Object[0]));
            }
        }
        return appCacheDir;
    }

    public static void checkmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private static PackageInfo packageInfo;

    public static PackageInfo packageInfo(Context context) {
        if (packageInfo == null) {
            try {
                packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                        0);
            } catch (NameNotFoundException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
        return packageInfo;
    }

    public static String versionName(Context context) {
        return packageInfo(context).versionName;
    }

    public static int versionCode(Context context) {
        return packageInfo(context).versionCode;
    }

    private static String imei;

    private static final String DEFAULT_IMEI = "00000000000000";

    private static String deviceId;

    /**
     * 手机的IMEI设备序列号
     * <p>
     * 第一次启动时会保存该序列号，可以频繁调用
     *
     * @return IMEI or "00000000000000" if error
     */
    public static String imei(final Context context) {
        if (imei == null) {
            // update cached imei when identity changed. including brand, model,
            // radio and system version
            String deviceIdentity = Build.VERSION.RELEASE + ";" + Build.MODEL + ";" + Build.BRAND;
            if (deviceIdentity.length() > 64) {
                deviceIdentity = deviceIdentity.substring(0, 64);
            }
            if (deviceIdentity.indexOf('\n') >= 0) {
                deviceIdentity = deviceIdentity.replace('\n', ' ');
            }

            String cachedIdentity = null;
            String cachedImei = null;
            try {
                // do not use file storage, use cached instead
                File path = new File(context.getCacheDir(), "cached_imei");
                FileInputStream fis = new FileInputStream(path);
                byte[] buf = new byte[1024];
                int l = fis.read(buf);
                fis.close();
                String str = new String(buf, 0, l, "UTF-8");
                int a = str.indexOf('\n');
                cachedIdentity = str.substring(0, a);
                int b = str.indexOf('\n', a + 1);
                cachedImei = str.substring(a + 1, b);
            } catch (Exception e) {
            }

            if (deviceIdentity.equals(cachedIdentity)) {
                imei = cachedImei;
            } else {
                imei = null;
            }

            // cache fail, read from telephony manager
            if (imei == null) {
                try {
                    TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    imei = tel.getDeviceId();
                    if (imei != null) {
                        if (imei.length() < 8) {
                            imei = null;
                        } else {
                            char c0 = imei.charAt(0);
                            boolean allSame = true;
                            for (int i = 0, n = imei.length(); i < n; i++) {
                                if (c0 != imei.charAt(i)) {
                                    allSame = false;
                                    break;
                                }
                            }
                            if (allSame)
                                imei = null;
                        }
                    }
                } catch (Exception e) {
                }
                if (imei != null) {
                    try {
                        File path = new File(context.getCacheDir(), "cached_imei");
                        FileOutputStream fos = new FileOutputStream(path);
                        String str = deviceIdentity + "\n" + imei + "\n";
                        fos.write(str.getBytes("UTF-8"));
                        fos.close();
                    } catch (Exception e) {
                    }
                } else {
                    File path = new File(context.getCacheDir(), "cached_imei");
                    path.delete();
                }
            }

            if (imei == null) {
                imei = DEFAULT_IMEI;
            }
        }
        return imei;
    }

    public static String getLocalMacAddress(final Context content) {
        WifiManager wifi = (WifiManager) content.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences sharedPreferences(final Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }


    public static String getDeviceId(final Context context) {
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        deviceId = sharedPreferences(context).getString("F029FrkL99", "");
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = imei(context);
        }
        if (TextUtils.isEmpty(deviceId) || DEFAULT_IMEI.equals(deviceId)) {
            deviceId = getLocalMacAddress(context);
        }
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = UUID.randomUUID().toString();
        }
        sharedPreferences(context).edit().putString("F029FrkL99", deviceId);
        Log.d("deviceId = " + deviceId);
        return deviceId;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     *
     * @param context
     * @return
     */
    public static boolean isApplicationForeground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (tasks != null && tasks.size() != 0) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(
                view, InputMethodManager.SHOW_FORCED);
    }

    public static void toggleSoftInput(final View view) {
        if (view == null) {
            return;
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        view.requestFocus();
                        ((InputMethodManager) view.getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        break;

                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        }.sendEmptyMessageDelayed(1, 300);
    }

    /**
     * you need to add the permission in manifest: <uses-permission
     * android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     */
    public static void createShortcut(Context context, String name, Bitmap icon, Intent intent) {
        Intent i = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        i.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        if (icon != null)
            i.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
        i.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        context.sendBroadcast(i);
    }

    public static void copy2ClipBoard(final Context context, String content) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(content);
    }

    public static boolean isIntentAvailable(final Context context, final Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            return true;
        }
        return false;
    }
}
