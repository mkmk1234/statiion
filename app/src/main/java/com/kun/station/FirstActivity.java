package com.kun.station;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kun.station.base.BaseActivity;
import com.kun.station.util.PreferencesUtils;
import com.kun.station.widget.DialogPop;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by kun on 16/6/18.
 */
public class FirstActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_device)
    EditText etDevice;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    String deviceID;

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_first);
        if (!TextUtils.isEmpty(PreferencesUtils.getString(FirstActivity.this, "deviceID", ""))) {
            Intent i = new Intent(FirstActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }


    @OnClick(R.id.btn_commit)
    @Override
    public void onClick(View v) {
        deviceID = etDevice.getText().toString();
        if (TextUtils.isEmpty(deviceID)) {
            Toast.makeText(this, "请输入设备号", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog();

    }


    private void showDialog() {
        final DialogPop dialogPop = new DialogPop(this, true);
        dialogPop.show("您输入的设备号为" + deviceID + ",点击确定后将不能修改！", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPop.dismiss();
                PreferencesUtils.putString(FirstActivity.this, "deviceID", deviceID);
                Intent i = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
