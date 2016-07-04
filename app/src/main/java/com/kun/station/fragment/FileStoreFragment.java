package com.kun.station.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.db.DbManager;
import com.kun.station.model.FileShowModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/6/4.
 */
public class FileStoreFragment extends BaseFragment{
    @Bind(R.id.list)
    ListView list;

    private DbManager mDbManager;
    private List<FileShowModel> mDataList = new ArrayList<FileShowModel>();
    private FileStoreAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbManager = DbManager.getInstace(getContext());
        getCursorData();
    }

    private void getCursorData() {
        mDataList = mDbManager.getStoreFiles();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_file, null);
        ButterKnife.bind(this, view);
        mAdapter = new FileStoreAdapter(getActivity());
        list.setAdapter(mAdapter);
        return view;
    }

    public void refreshData(){
        mDataList.clear();
        getCursorData();
        mAdapter.notifyDataSetChanged();
    }

    private class FileStoreAdapter extends BaseAdapter{
        private LayoutInflater mInflater;

        public FileStoreAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
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
            ViewHolder mHolder;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.item_store_file, null);
                mHolder = new ViewHolder();
                mHolder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
                mHolder.pathTv = (TextView) convertView.findViewById(R.id.tv_path);
                mHolder.imageIv = (ImageView) convertView.findViewById(R.id.iv_image);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            FileShowModel item = mDataList.get(position);
            mHolder.nameTv.setText(item.fileName);
            mHolder.imageIv.setImageResource(item.imageId);
            mHolder.pathTv.setText(item.dirName);
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    FileShowModel itemFile = mDataList.get(position);
                    if (itemFile != null) {
                        if (mDbManager.isStore(itemFile.dirName, itemFile.fileName)) {
                            Toast.makeText(getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                        mDbManager.updateStore(itemFile.dirName, itemFile.fileName);
                        refreshData();
                    }
                    return true;
                }
            });
            return convertView;
        }

        class ViewHolder{
            TextView nameTv;
            TextView pathTv;
            ImageView imageIv;
        }
    }
}
