package com.kun.station.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.kun.station.R;

public class LoadingDialog extends Dialog {

	public LoadingDialog(Context context, String message) {
		super(context, R.style.LoadingDialog);
		initDialog(message);
	}

	private void initDialog(String message) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_loading);
		if (TextUtils.isEmpty(message)){
			findViewById(android.R.id.message).setVisibility(View.GONE);
		} else {
			((TextView)findViewById(android.R.id.message)).setText(message);
		}
		DisplayMetrics displayMetrics = getContext().getResources()
				.getDisplayMetrics();
		Window window = getWindow();
		LayoutParams attributes = window.getAttributes();
		attributes.alpha = 0.3f;
		attributes.width = displayMetrics.widthPixels;
		attributes.height = displayMetrics.heightPixels;
		window.setAttributes(attributes);
		setCanceledOnTouchOutside(false);
	}
}
