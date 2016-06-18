package com.kun.station.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kun.station.R;
import com.kun.station.model.FileModel;
import com.kun.station.model.FileShowModel;
import com.kun.station.util.FileUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by admin on 2016/6/18.
 */
public class DownLoadFileAdapter extends BaseAdapter{
    private ArrayList<FileModel> mOnLineList;
    private ArrayList<FileShowModel> mDataList;
    private LayoutInflater mInflater;
    private String currentPath;
    private String rootPath;

    public DownLoadFileAdapter(Context context, ArrayList<FileModel> dataList, ArrayList<FileShowModel> fileDataList){
        this.mOnLineList = dataList;
        mDataList = fileDataList;
        mInflater = LayoutInflater.from(context);
        rootPath = FileUtil.getExternalDir().getAbsolutePath() + "/";
    }

    public void setCurrentPath(String path){
        currentPath = path;
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
    public void notifyDataSetChanged() {
        if(!TextUtils.isEmpty(currentPath) && mDataList != null){
            for(FileModel itemModel : mOnLineList){
                if (currentPath.equals(rootPath + itemModel.dirName)){
                    FileShowModel matchFile = new FileShowModel();
                    matchFile.dir = itemModel.fileName;
                    matchFile.path = rootPath + "/" + itemModel.dirName + "/" + itemModel.fileName;
                    mDataList.add(matchFile);
                }
            }
        }
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder mHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_download_file, null);
            mHolder = new Holder();
            mHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
            mHolder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            mHolder.selectCb = (CheckBox) convertView.findViewById(R.id.cb_checked);
            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }
        FileShowModel fileItem = mDataList.get(position);
        mHolder.imageView.setImageResource(fileItem.imageId);
        mHolder.nameTv.setText(fileItem.dir);
        return convertView;
    }

    class Holder {
        ImageView imageView;
        TextView nameTv;
        CheckBox selectCb;
    }
}
