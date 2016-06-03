package com.kun.station.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kun.station.MyApplication;
import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.db.DbManager;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kun on 16/5/25.
 */
public class FileFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.tv_dir_path)
    TextView pathTv;
    @Bind(R.id.btn_store)
    TextView storeBtn;

    private ArrayList<FileItem> dataList = new ArrayList<>();
    private FileListAdapter mAdapter;
    private DbManager mDbManager;
    private FileItem mSelectFileItem;
    private String currentPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbManager = MyApplication.getInstance().getDbManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new FileListAdapter(getActivity(), dataList);
        list.setAdapter(mAdapter);
        loadData("/");

        list.setOnItemClickListener(this);
        return view;
    }

    @OnClick(R.id.btn_store)
    void handleClick(View v){
        if (mSelectFileItem != null){
            mDbManager.insertItemFile(mSelectFileItem.path, mSelectFileItem.dir, !mSelectFileItem.isStore, mSelectFileItem.imageId != R.drawable.file);
            mAdapter.notifyDataSetChanged();
            v.setVisibility(View.GONE);
            loadData(currentPath);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadData(String path) {
        currentPath = path;
        pathTv.setText(String.format(getResources().getString(R.string.dir_path), path));
        dataList.removeAll(dataList);
        File file = new File(path);
        if (!"/".equals(path)) {
            addBack(file);
        }
        File[] fileList = file.listFiles();
        if (fileList != null) {
            addFileAndDir(fileList);
        }
        mAdapter.notifyDataSetChanged();
    }


    private void addBack(File file) {
        FileItem item = new FileItem();
        item.imageId = R.drawable.back;
        item.dir = "返回上一级";
        item.path = file.getParent();
        item.isStore = false;
        dataList.add(item);
    }

    private void addFileAndDir(File[] fileList) {
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile() && !fileList[i].canRead()) {
                continue;
            }
            FileItem item = new FileItem();
            item.dir = fileList[i].getName();
            item.path = fileList[i].getAbsolutePath();
            if (fileList[i].isDirectory()) {
                if (fileList[i].list() != null) {
                    item.imageId = R.drawable.dir;
                } else {
                    item.imageId = R.mipmap.ic_launcher;
                }
                item.isStore = false;
            } else {
                item.imageId = R.drawable.file;
                item.isStore = mDbManager.isStoreFile(item.path, item.dir);
            }
            dataList.add(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileItem itemFile = dataList.get(position);
        if (itemFile.imageId != R.drawable.file) {
            loadData(itemFile.path);
            storeBtn.setVisibility(View.GONE);
            mSelectFileItem = null;
        } else {
            storeBtn.setVisibility(View.VISIBLE);
            storeBtn.setText(itemFile.isStore ? "取消收藏":"收藏");
            mSelectFileItem = itemFile;
        }
    }

    class FileItem {
        public int imageId;
        public String dir;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder mHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_file, null);
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
            mHolder.nameTv.setText(fileItem.dir);
            mHolder.storeIv.setVisibility(fileItem.isStore ? View.VISIBLE : View.GONE);
            return convertView;
        }

        class Holder {
            ImageView imageView;
            TextView nameTv;
            ImageView storeIv;
        }
    }
}
