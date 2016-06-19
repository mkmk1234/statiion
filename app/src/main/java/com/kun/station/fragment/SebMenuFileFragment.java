package com.kun.station.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.util.FileUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/6/19.
 */
public class SebMenuFileFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    public static final String ExtraPATH = "extra_path";
    @Bind(R.id.list)
    ListView list;


    private ArrayList<FileItem> dataList = new ArrayList<>();
    private FileListAdapter mAdapter;
    private FileItem mSelectFileItem;
    private String currentPath;
    private String firstPath;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new FileListAdapter(getActivity(), dataList);
        list.setAdapter(mAdapter);
        firstPath = getStringParam(ExtraPATH);
        if (TextUtils.isEmpty(firstPath)) {
            firstPath = FileUtil.getExternalDir().getPath();
        }
        loadData(firstPath);
        list.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadData(String path) {
        currentPath = path;
        dataList.removeAll(dataList);
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
            item.dir = fileList[i].getName();
            item.path = fileList[i].getAbsolutePath();
            if (fileList[i].isDirectory()) {
                if (fileList[i].list() != null) {
                    item.imageId = R.drawable.dir;
                } else {
//                    item.imageId = R.mipmap.ic_launcher;
                    item.imageId = R.drawable.dir;
                }
                item.isStore = false;
            } else {
                if (item.dir.endsWith(".pdf")) {
                    item.imageId = R.drawable.pdf_pic;
                } else if (item.dir.endsWith(".doc")) {
                    item.imageId = R.drawable.word_pic;
                } else if (item.dir.endsWith(".png") || item.dir.endsWith(".jpg")) {
                    item.imageId = R.drawable.png_pic;
                } else {
                    item.imageId = R.drawable.file;
                }
            }
            dataList.add(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileItem itemFile = dataList.get(position);
        if (itemFile.imageId == R.drawable.dir || itemFile.imageId == R.drawable.back) {
            loadData(itemFile.path);
            mSelectFileItem = null;
        } else {
            try {
                if (itemFile.dir.contains("doc")) {
                    startActivity(FileUtil.getWordFileIntent(itemFile.path));
                }
                if (itemFile.dir.contains("pdf")) {
                    startActivity(FileUtil.getPdfFileIntent(itemFile.path));
                }
                if (itemFile.dir.contains("xls")) {
                    startActivity(FileUtil.getExcelFileIntent(itemFile.path));
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "请安装软件打开文件。", Toast.LENGTH_SHORT).show();
            }
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
