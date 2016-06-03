package com.kun.station.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kun.station.MyApplication;
import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.db.DbManager;
import com.kun.station.db.SqlConstants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/6/4.
 */
public class FileStoreFragment extends BaseFragment{
    @Bind(R.id.list)
    ListView list;

    private DbManager mDbManager;
    private ArrayList<FileItem> mDataList = new ArrayList<FileItem>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbManager = MyApplication.getInstance().getDbManager();
        Cursor cursor = mDbManager.getStoreList(true);
        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                FileItem item = new FileItem();
                item.fileName = cursor.getString(cursor.getColumnIndex(SqlConstants.Key_FileName));
                item.filePath = cursor.getString(cursor.getColumnIndex(SqlConstants.Key_FilePath));
                mDataList.add(item);
            }
            cursor.close();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_file, null);
        ButterKnife.bind(this, view);
        list.setAdapter(new FileStoreAdapter(getActivity(), mDataList));
        return view;
    }

    private class FileItem{
        String fileName;
        String filePath;
    }

    private class FileStoreAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        private ArrayList<FileItem> mDataList;

        public FileStoreAdapter(Context context, ArrayList<FileItem> dataList){
            mInflater = LayoutInflater.from(context);
            mDataList = dataList;
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
            ViewHolder mHolder;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.item_store_file, null);
                mHolder = new ViewHolder();
                mHolder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
                mHolder.pathTv = (TextView) convertView.findViewById(R.id.tv_path);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            FileItem item = mDataList.get(position);
            mHolder.nameTv.setText(item.fileName);
            mHolder.pathTv.setText(item.filePath);
            return convertView;
        }

        class ViewHolder{
            TextView nameTv;
            TextView pathTv;
        }
    }
}
