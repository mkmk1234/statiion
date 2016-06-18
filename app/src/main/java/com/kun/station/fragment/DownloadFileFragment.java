package com.kun.station.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.kun.station.MyApplication;
import com.kun.station.R;
import com.kun.station.adapter.DownLoadFileAdapter;
import com.kun.station.base.BaseFragment;
import com.kun.station.model.FileModel;
import com.kun.station.model.FileShowModel;
import com.kun.station.response.MenuItemResponse;
import com.kun.station.util.FileUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by admin on 2016/6/18.
 */
public class DownloadFileFragment extends BaseFragment{
    private ArrayList<FileModel> fileList;
    private Context mContext;
    private ArrayList<FileShowModel> fileShowList = new ArrayList<>();
    private DownLoadFileAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        fileList = MyApplication.mGson.fromJson(FileUtil.loadRawString(mContext, R.raw.localdata), new TypeToken<ArrayList<FileModel>>() {}.getType());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_file, null);
        ListView listView = (ListView) view.findViewById(R.id.lv_list);
        listView.setOnItemClickListener(mItemClickListener);
        mAdapter = new DownLoadFileAdapter(mContext, fileList, fileShowList);
        listView.setAdapter(mAdapter);
        loadData(FileUtil.getExternalDir().getAbsolutePath());
        return view;
    }

    private void loadData(String path) {
//        pathTv.setText(String.format(getResources().getString(R.string.dir_path), path.replace(FileUtil.getExternalDir().getPath(), "/乔司站")));
        fileShowList.removeAll(fileShowList);
        File file = new File(path);
//        if (!"/".equals(path)) {
        File[] fileList = file.listFiles();
        if (fileList != null) {
            addDir(fileList);
        }
        mAdapter.setCurrentPath(path);
        mAdapter.notifyDataSetChanged();
    }

    private void addDir(File[] fileList) {
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile() && !fileList[i].canRead()) {
                continue;
            }
            if (!fileList[i].isDirectory()) {
                continue;
            }
            FileShowModel item = new FileShowModel();
            item.dir = fileList[i].getName();
            item.path = fileList[i].getAbsolutePath();
            if (fileList[i].list() != null) {
                item.imageId = R.drawable.dir;
            } else {
//                    item.imageId = R.mipmap.ic_launcher;
                item.imageId = R.drawable.dir;
            }
//            } else {
//                if (item.dir.endsWith(".pdf")) {
//                    item.imageId = R.drawable.pdf_pic;
//                } else if (item.dir.endsWith(".doc")) {
//                    item.imageId = R.drawable.word_pic;
//                } else if (item.dir.endsWith(".png")) {
//                    item.imageId = R.drawable.png_pic;
//                } else {
//                    item.imageId = R.drawable.file;
//                }
//            }
            fileShowList.add(item);
        }
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FileShowModel itemFile = fileShowList.get(position);
            if (itemFile.imageId == R.drawable.dir) {
                loadData(itemFile.path);
            } else {
            }
        }
    };
}