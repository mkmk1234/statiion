package com.kun.station;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kun.station.base.BaseActivity;

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

    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_first);
    }


    @OnClick(R.id.btn_commit)
    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(etDevice.getText().toString())) {
            Toast.makeText(this, "请输入设备号", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
