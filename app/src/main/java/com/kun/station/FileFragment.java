package com.kun.station;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kun on 16/5/25.
 */
public class FileFragment extends Fragment implements AdapterView.OnItemClickListener{
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.tv_dir_path)
    TextView pathTv;

    private ArrayList<FileItem> dataList = new ArrayList<>();
    private FileListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new FileListAdapter(getActivity(), dataList);
        list.setAdapter(mAdapter);
        loadData("/");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadData(String path) {
        pathTv.setText(String.format(getResources().getString(R.string.dir_path), path));
        dataList.removeAll(dataList);
        File file = new File(path);
        File[] fileList = file.listFiles();
        if(fileList!=null){
            addFileAndDir(fileList);
        }
        if(!"/".equals(path)){
            addBack(file);
        }
        mAdapter.notifyDataSetChanged();
    }


    private void addBack(File file) {
        FileItem item = new FileItem();
        item.imageId = R.drawable.back;
        item.dir = "返回上一级";
        item.path = file.getParent();
        dataList.add(item);
    }

    private void addFileAndDir(File[] fileList) {
        for(int i = 0 ; i < fileList.length ; i++){
            if(fileList[i].isFile() && !fileList[i].canRead()){
                continue;
            }
            FileItem item = new FileItem();
            if(fileList[i].isDirectory()){
                if(fileList[i].list() != null){
                    item.imageId = R.drawable.dir;
                }else{
                    item.imageId = R.mipmap.ic_launcher;
                }
            }else{
                item.imageId = R.drawable.file;
            }
            item.dir = fileList[i].getName();
            item.path = fileList[i].getAbsolutePath();
            dataList.add(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (dataList.get(position).imageId != R.drawable.file){
            loadData(dataList.get(position).path);
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

        public FileListAdapter(Context context, ArrayList<FileItem> dataList){
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
            if (convertView == null){
                convertView = mInflater.inflate(R.layout.item_file, null);
                mHolder = new Holder();
                mHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
                mHolder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(mHolder);
            } else {
                mHolder = (Holder) convertView.getTag();
            }
            FileItem fileItem = mDataList.get(position);
            mHolder.imageView.setImageResource(fileItem.imageId);
            mHolder.nameTv.setText(fileItem.dir);
            return convertView;
        }

        class Holder{
            ImageView imageView;
            TextView nameTv;
        }
    }
}
