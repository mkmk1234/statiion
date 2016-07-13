package com.kun.station.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.db.DbManager;
import com.kun.station.model.DeviceModel;
import com.kun.station.model.FileShowModel;
import com.kun.station.network.NetworkApi;
import com.kun.station.util.FileUtil;
import com.kun.station.util.ToastUtils;
import com.kun.station.widget.CustomPop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/5/25.
 */
public class CatalogFragment extends BaseFragment {
    public static final String ExtraPATH = "extra_path";
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.tv_dir_path)
    TextView pathTv;

    private ArrayList<FileItem> dataList = new ArrayList<>();
    private FileListAdapter mAdapter;
    private DbManager mDbManager;
    private String currentPath;
    private String firstPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbManager = DbManager.getInstace(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new FileListAdapter(getActivity(), dataList);
        list.setAdapter(mAdapter);
        firstPath = getStringParam(ExtraPATH);
        if (TextUtils.isEmpty(firstPath)) {
            firstPath = FileUtil.getExternalDir().getPath();
        }
        loadData(firstPath);
        //list.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadData(String path) {
        currentPath = path;
        pathTv.setText(String.format(getResources().getString(R.string.dir_path), path.replace(FileUtil.getExternalDir().getPath(), "/乔司站")));
        dataList.clear();
        File file = new File(path);
        File[] fileList = file.listFiles();
        if (fileList != null) {
            addFileAndDir(fileList);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void addFileAndDir(File[] fileList) {
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile() && !fileList[i].canRead()) {
                continue;
            }
            FileItem item = new FileItem();
            item.name = fileList[i].getName();
            item.path = fileList[i].getAbsolutePath();
            if (fileList[i].isDirectory()) {
                item.imageId = R.drawable.dir;
                item.isStore = false;
            } else {
                if (item.name.endsWith(".pdf")) {
                    item.imageId = R.drawable.pdf_pic;
                } else if (item.name.endsWith(".doc")) {
                    item.imageId = R.drawable.word_pic;
                } else if (item.name.endsWith(".png")) {
                    item.imageId = R.drawable.png_pic;
                } else {
                    item.imageId = R.drawable.file;
                }
                item.isStore = mDbManager.isStore(getRealPath(item.path, item.name), item.name);
            }
            dataList.add(item);
        }
    }

    private String getRealPath(String path, String name) {
        return "/" + path.replace("/" + name, "").replace(FileUtil.getExternalDir().getPath() + "/", "");

    }


    public void back() {
        if (currentPath.equals(firstPath)) {
            return;
        }
        File file = new File(currentPath);
        String path = file.getParent();
        loadData(path);
    }

    class FileItem {
        public int imageId;
        public String name;
        public String path;
        public boolean isStore;
    }

    class FileListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private ArrayList<FileItem> mDataList;

        public FileListAdapter(Context context, ArrayList<FileItem> dataList) {
            mInflater = LayoutInflater.from(context);
            this.mDataList = dataList;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder mHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_catalog, null);
                mHolder = new Holder();
                mHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
                mHolder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
                mHolder.storeIv = (ImageView) convertView.findViewById(R.id.iv_store);
                convertView.setTag(mHolder);
            } else {
                mHolder = (Holder) convertView.getTag();
            }
            FileItem fileItem = mDataList.get(position);
            mHolder.imageView.setImageResource(fileItem.imageId);
            mHolder.nameTv.setText(fileItem.name);
            mHolder.storeIv.setVisibility(fileItem.isStore ? View.VISIBLE : View.GONE);
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    FileItem itemFile = dataList.get(position);
                    if (itemFile != null && itemFile.imageId != R.drawable.dir) {
                        List<FileShowModel> list = mDbManager.getFile(getRealPath(itemFile.path, itemFile.name), itemFile.name);
                        FileShowModel fileShowModel = null;
                        if (list.size() > 0) {
                            fileShowModel = list.get(0);
                        } else {
                            return true;
                        }
                        final FileShowModel finalFileShowModel = fileShowModel;
                        NetworkApi.changeCollection(fileShowModel.fileShowID + "", finalFileShowModel.isStore == 1 ? "0" : "1",
                                new Response.Listener<DeviceModel>() {
                                    @Override
                                    public void onResponse(DeviceModel response) {
                                        if (finalFileShowModel.isStore == 1) {
                                            ToastUtils.showToast("取消收藏");
                                        } else {
                                            ToastUtils.showToast("收藏成功");
                                        }
                                        mDbManager.updateStore(finalFileShowModel.dirName, finalFileShowModel.fileName);
                                        loadData(currentPath);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ToastUtils.showToast(error.getMessage());
                                    }
                                });
                    }
                    return true;
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileItem itemFile = dataList.get(position);
                    if (itemFile.imageId == R.drawable.dir) {
                        loadData(itemFile.path);
                    }
                    try {
                        if (itemFile.name.endsWith("doc")) {
                            startActivity(FileUtil.getWordFileIntent(itemFile.path));
                        }
                        if (itemFile.name.endsWith("pdf")) {
                            startActivity(FileUtil.getPdfFileIntent(itemFile.path));
                        }
                        if (itemFile.name.endsWith("xls") || itemFile.name.endsWith("xlsx") || itemFile.name.endsWith("xlsm")) {
                            startActivity(FileUtil.getExcelFileIntent(itemFile.path));
                        }
                        if (itemFile.name.endsWith("jpg") || itemFile.name.endsWith("png")) {
                            CustomPop customPop = new CustomPop(getActivity(), CustomPop.Type.Type_Img);
                            customPop.setImageResource(R.drawable.home_viewpager_one);
                            customPop.show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "请安装软件打开文件。", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            return convertView;
        }

        class Holder {
            ImageView imageView;
            TextView nameTv;
            ImageView storeIv;
        }
    }
}
