package com.kun.station;

import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kun.station.base.BaseActivity;
import com.kun.station.db.DbManager;
import com.kun.station.model.DeviceModel;
import com.kun.station.model.DirectoryModel;
import com.kun.station.model.MenuItemModel;
import com.kun.station.model.MenuModel;
import com.kun.station.model.SubMenuModel;
import com.kun.station.network.NetworkApi;
import com.kun.station.response.MenuItemResponse;
import com.kun.station.util.AndroidUtils;
import com.kun.station.util.FileUtil;
import com.kun.station.util.PreferencesUtils;
import com.kun.station.util.ToastUtils;
import com.kun.station.widget.DialogPop;

import java.io.File;
import java.util.List;

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
    String erialId;
    List<DirectoryModel> directoryModelList;


    @Override
    protected void onSetContentView() {
        setContentView(R.layout.activity_first);
        if (!TextUtils.isEmpty(PreferencesUtils.getString(FirstActivity.this, "erialId", ""))) {
            Intent i = new Intent(FirstActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            ToastUtils.showToast("delete");
            FileUtil.deleteAllDir(new File(Environment.getExternalStorageDirectory(), "乔司站"));
        }
    }


    @OnClick(R.id.btn_commit)
    @Override
    public void onClick(View v) {
        erialId = etDevice.getText().toString();
        AndroidUtils.hideKeyboard(v);
        showDialog();
    }


    private void showDialog() {
        final DialogPop dialogPop = new DialogPop(this, true);
        dialogPop.show("您输入的设备号为" + erialId + ",点击确定后将不能修改！", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPop.dismiss();
                NetworkApi.bindDevice(AndroidUtils.getDeviceId(FirstActivity.this), erialId, new Response.Listener<DeviceModel>() {
                    @Override
                    public void onResponse(DeviceModel response) {
                        MyApplication.erialId = response.erialId;
                        PreferencesUtils.putString(FirstActivity.this, "erialId", response.erialId);
                        PreferencesUtils.putString(FirstActivity.this, "deptName", response.deptName);
                        PreferencesUtils.putString(FirstActivity.this, "station", response.station);
                        PreferencesUtils.putString(FirstActivity.this, "equipmentNumber", response.equipmentNumber);
                        initApp();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FirstActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void initApp() {
        NetworkApi.getMenuInfo(new Response.Listener<MenuItemResponse>() {
            @Override
            public void onResponse(MenuItemResponse response) {
                directoryModelList = response.getDirList();
                initMenuModel(response.getMenuList());
                for (int i = 0; i < response.getDirList().size(); i++) {
                    DbManager.getInstace(FirstActivity.this).insertDir(response.getDirList().get(i));
                }
                createDir();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("初始化失败。");
            }
        });
    }

    private void initMenuModel(List<MenuItemModel> list) {
        for (int i = 0; i < list.size(); i++) {
            MenuItemModel menuItemModel = list.get(i);
            if (menuItemModel.getType() == 1) {
                List<SubMenuModel> subMenuModelList = menuItemModel.getSubMenuList();
                for (int j = 0; j < subMenuModelList.size(); j++) {
                    subMenuModelList.get(j).setMenuId(menuItemModel.getId());
                    DbManager.getInstace(FirstActivity.this).insertSubMenu(subMenuModelList.get(j));
                }
            }
            MenuModel menuModel = new MenuModel(menuItemModel.getId(), menuItemModel.getIcon(), menuItemModel.getTitle(), menuItemModel.getType());
            DbManager.getInstace(FirstActivity.this).insertMenu(menuModel);
        }
    }


    private void startMainActivity() {
        Intent i = new Intent(FirstActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void createDir() {
        File ext = new File(Environment.getExternalStorageDirectory(), "乔司站");
        if (!ext.exists()) {
            File stationDir = FileUtil.getExternalDir();
            File dirFile;
            for (DirectoryModel directoryModel : directoryModelList) {
                dirFile = new File(stationDir, directoryModel.getDirPath());
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
            }
        }
        startMainActivity();
    }

}
