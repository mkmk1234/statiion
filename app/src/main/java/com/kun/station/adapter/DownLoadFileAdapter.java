package com.kun.station.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kun.station.R;
import com.kun.station.model.FileModel;
import com.kun.station.model.FileShowModel;
import com.kun.station.util.FileUtil;

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
    private ArrayList<FileShowModel> selectPaths = new ArrayList<>();

    public DownLoadFileAdapter(Context context, ArrayList<FileModel> dataList, ArrayList<FileShowModel> fileDataList){
        this.mOnLineList = dataList;
        mDataList = fileDataList;
        mInflater = LayoutInflater.from(context);
        rootPath = FileUtil.getExternalDir().getAbsolutePath() + "/";
    }

    public void setCurrentPath(String path){
        currentPath = path;
    }

    public String getCurrentPath(){
        return currentPath;
    }

    public ArrayList<FileShowModel> getSelectPaths(){
        return selectPaths;
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
//        if(!TextUtils.isEmpty(currentPath) && mDataList != null){
//            for(FileModel itemModel : mOnLineList){
//                if (currentPath.equals(rootPath + itemModel.dirName)){
//                    FileShowModel matchFile = new FileShowModel();
//                    matchFile.dir = itemModel.fileName;
//                    matchFile.path = rootPath + "/" + itemModel.dirName + "/" + itemModel.fileName;
//                    if (matchFile.dir.endsWith(".pdf")) {
//                        matchFile.imageId = R.drawable.pdf_pic;
//                    } else if (matchFile.dir.endsWith(".doc")) {
//                        matchFile.imageId = R.drawable.word_pic;
//                    } else if (matchFile.dir.endsWith(".png")) {
//                        matchFile.imageId = R.drawable.png_pic;
//                    } else {
//                        matchFile.imageId = R.drawable.file;
//                    }
//                    mDataList.add(matchFile);
//                }
////            }／／}
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
        final FileShowModel fileItem = mDataList.get(position);
        //／／   mHolder.selectCb.setChecked(selectPaths.contains(fileItem.path));
        mHolder.selectCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectPaths.add(fileItem);
                } else {
                    selectPaths.remove(fileItem);
                }
            }
        });
        mHolder.imageView.setImageResource(fileItem.imageId);
        // mHolder.nameTv.setText(fileItem.dir);
        return convertView;
    }

    class Holder {
        ImageView imageView;
        TextView nameTv;
        CheckBox selectCb;
    }
}
