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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.util.FileUtil;
import com.kun.station.widget.CustomPop;

import java.io.File;
import java.util.ArrayList;

public class FileSearchFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private EditText mEditText;
    private String filePath;
    private ArrayList<FileItem> dataList = new ArrayList<>();
    private FileListAdapter mAdapter;
    private View emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_search, null);
        mEditText = (EditText) view.findViewById(R.id.et_search);
        view.findViewById(R.id.btn_search).setOnClickListener(this);
        ListView mListView = (ListView) view.findViewById(R.id.lv_result);
        mAdapter = new FileListAdapter(getActivity(), dataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        emptyView = view.findViewById(R.id.tv_empty);
        filePath = getStringParam(CatalogFragment.ExtraPATH);
        if (TextUtils.isEmpty(filePath)) {
            filePath = FileUtil.getExternalDir().getPath();
        }
        return view;
    }

    private void loadData(String path, String searchKey){
        for (File itemFile : new File(path).listFiles()){
            if (itemFile.isFile()){
                if(itemFile.getName().contains(searchKey)){
                    FileItem item = new FileItem();
                    item.dir = itemFile.getName();
                    item.path = itemFile.getAbsolutePath();
                    if (item.dir.endsWith(".pdf")) {
                        item.imageId = R.drawable.pdf_pic;
                    } else if (item.dir.endsWith(".doc")) {
                        item.imageId = R.drawable.word_pic;
                    } else if (item.dir.endsWith(".png")) {
                        item.imageId = R.drawable.png_pic;
                    } else {
                        item.imageId = R.drawable.file;
                    }
                    dataList.add(item);
                }
            } else if (itemFile.isDirectory()){
                loadData(itemFile.getAbsolutePath(), searchKey);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                if (!TextUtils.isEmpty(mEditText.getText())){
                    dataList.clear();
                    loadData(filePath, mEditText.getText().toString());
                    mAdapter.notifyDataSetChanged();
                    emptyView.setVisibility(mAdapter.isEmpty() ? View.VISIBLE:View.GONE);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileItem itemFile = dataList.get(position);
        try {
            if (itemFile.dir.endsWith("doc")) {
                startActivity(FileUtil.getWordFileIntent(itemFile.path));
            }
            if (itemFile.dir.endsWith("pdf")) {
                startActivity(FileUtil.getPdfFileIntent(itemFile.path));
            }
            if (itemFile.dir.endsWith("xls")) {
                startActivity(FileUtil.getExcelFileIntent(itemFile.path));
            }
            if (itemFile.dir.endsWith("jpg") || itemFile.dir.endsWith("png")) {
                CustomPop customPop = new CustomPop(getActivity(), CustomPop.Type.Type_Img);
                customPop.setImageResource(R.drawable.home_viewpager_one);
                customPop.show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "请安装软件打开文件。", Toast.LENGTH_SHORT).show();
        }
    }

    class FileItem {
        public int imageId;
        public String dir;
        public String path;
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
            return convertView;
        }

        class Holder {
            ImageView imageView;
            TextView nameTv;
            ImageView storeIv;
        }
    }
}
