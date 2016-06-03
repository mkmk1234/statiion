package com.kun.station.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class DisplayUtils {

    private static int screenWidth;
    private static int screenHeight;
    private static int contentHeight;
    private static int titleBarHeight;
    private static int statusBarHeight;
    private static DisplayMetrics dm;

    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (dm == null) {
            dm = new DisplayMetrics();
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(dm);
        }
        return dm;
    }

    public static int getScreenWidth(Context context) {
        if (screenWidth == 0) {
            screenWidth = getDisplayMetrics(context).widthPixels;
        }
        return screenWidth;
    }

    public static int getScreenHeight(Context context) {
        if (screenHeight == 0) {
            screenHeight = getDisplayMetrics(context).heightPixels;
        }
        return screenHeight;
    }

    public static int getContentViewHeight(Activity activity) {
        if (contentHeight == 0) {
            contentHeight = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getHeight();
        }
        return contentHeight;
    }

    public static int getTitleBarHeight(Activity activity) {
        if (titleBarHeight == 0) {
            int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
            titleBarHeight = contentTop - getStatusBarHeight((Context) activity);
        }
        return titleBarHeight;
    }

    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                if (context instanceof Activity) {
                    statusBarHeight = getStatusBarHeight((Activity) context);
                }
            }
        }
        return statusBarHeight;
    }

    private static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * Determines if given points are inside view
     *
     * @param x    - x coordinate of point
     * @param y    - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    public static boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        // point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth())) && (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return (int) dipValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return (int) pxValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context
     * @param px
     * @return
     */
    public static float px2sp(Context context, Float px) {
        if (context == null) {
            return px.intValue();
        }
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    /**
     * @param context
     * @param sp
     * @return
     */
    public static float sp2px(Context context, float sp) {
        if (context == null) {
            return sp;
        }
        Resources r = context.getResources();
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r.getDisplayMetrics());
        return size;
    }

}
