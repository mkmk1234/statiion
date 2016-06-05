package com.kun.station.util;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kun.station.R;


public class DialogUtils {

    public static Dialog showBottomAlert(final Context context, CharSequence message, View.OnClickListener confirmListener) {
        return buildDialog(context, message, "确认", "取消", -1, confirmListener, null, false, 0);
    }

    public static Dialog showBottomAlert(final Context context, CharSequence message, View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        return buildDialog(context, message, "确认", "取消", -1, confirmListener, cancelListener, false, 0);
    }

    public static Dialog showBottomAlert(final Context context, CharSequence message, CharSequence confimButton, CharSequence cancelTitle, View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        return buildDialog(context, message, confimButton, cancelTitle, -1, confirmListener, cancelListener, false, 0);
    }

    public static Dialog showCenterAlert(final Context context, CharSequence message, View.OnClickListener confirmListener) {
        return buildDialog(context, message, "确认", "取消", -1, confirmListener, null, false, 1);
    }

    public static Dialog showCenterAlert(final Context context, CharSequence message, View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        return buildDialog(context, message, "确认", "取消", -1, confirmListener, cancelListener, false, 1);
    }

    public static Dialog showCenterAlert(final Context context, CharSequence message, CharSequence confimButton, CharSequence cancelTitle, View.OnClickListener confirmListener, final View.OnClickListener cancelListener) {
        return buildDialog(context, message, confimButton, cancelTitle, -1, confirmListener, cancelListener, false, 1);
    }

    public static Dialog showOneButtonCenterAlert(final Context context, CharSequence message, CharSequence confimButton, View.OnClickListener confirmListener) {
        return buildDialog(context, message, confimButton, "", -1, confirmListener, null, false, 1);
    }

    public static Dialog showTwoLineBottomAlert(final Context context, CharSequence text1, CharSequence text2, View.OnClickListener listener1, View.OnClickListener listener2) {
        return buildDialog(context, "", text1, text2, -1, listener1, listener2, false, 2);
    }

    private static Dialog buildDialog(final Context context, CharSequence message, CharSequence confirmButton, CharSequence cancelButton, int animationResId,
                                      final View.OnClickListener confirmListener, final View.OnClickListener cancelListener, boolean outsideCancelable, int alertType) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        Window window = dialog.getWindow();
        if (animationResId > 0) {
            window.setWindowAnimations(animationResId);
        }

        View view = null;
        if (alertType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_bottom_alert_view, null);
            view.findViewById(R.id.custom_bottom_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else if (alertType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_center_alert_view, null);
        } else if (alertType == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_bottom_two_line_alert_view, null);
            view.findViewById(R.id.custom_bottom_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        if (view == null) {
            Log.toast(context, "alert type is wrong");
            return null;
        }
        ((TextView) view.findViewById(R.id.message)).setText(message);

        final TextView cancel = (TextView) view.findViewById(R.id.cancel);
        cancel.setText(cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.cancel();
                if (cancelListener != null) {
                    cancelListener.onClick(view);
                }
            }
        });

        final TextView confirm = (TextView) view.findViewById(R.id.confirm);
        confirm.setText(confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (confirmListener != null) {
                    confirmListener.onClick(view);
                }
            }
        });
        if (TextUtils.isEmpty(cancelButton)) {
            cancel.setVisibility(View.GONE);
            view.findViewById(R.id.btn_divider).setVisibility(View.GONE);
        } else {
            cancel.setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_divider).setVisibility(View.VISIBLE);
        }
        dialog.show();
        dialog.setContentView(view);

        if (alertType == 0 || alertType == 2) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = DisplayUtils.getScreenWidth(context);
            window.setAttributes(lp);
        }

        dialog.setCanceledOnTouchOutside(outsideCancelable);
        return dialog;
    }
}
