package com.kun.station.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.db.DbManager;
import com.kun.station.model.FileShowModel;
import com.kun.station.util.FileUtil;
import com.kun.station.widget.CustomPop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kun on 16/6/19.
 */
public class NewDownloadFileFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    @Bind(R.id.lv_list)
    ListView lvList;
    @Bind(R.id.btn_clear)
    Button btnClear;
    private DownloadFileAdapter mAdapter;
    boolean isDownload;
    private List<FileShowModel> fileShowList;
    private List<FileShowModel> downLoadList;
    private List<FileShowModel> hasDownLoadList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (int i = 0; i < downLoadList.size(); i++) {
                if (fileShowList.get(i).isDownload) {
                    continue;
                }
                downLoadList.get(i).progress = downLoadList.get(i).progress + 5;
                fileShowList.get(i).progress = downLoadList.get(i).progress;
                if (downLoadList.get(i).progress >= 100) {
                    File dir = FileUtil.getExternalDir();
                    File dirFile1 = new File(dir, downLoadList.get(i).dirName);
                    String filename = downLoadList.get(i).fileName;
                    fileShowList.get(i).isDownload = true;
                    hasDownLoadList.add(fileShowList.get(i));
                    try {
                        new File(dirFile1, filename).createNewFile();
                        DbManager.getInstace(getContext()).updateFile(fileShowList.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            if (hasDownLoadList.size() == fileShowList.size()) {
                isDownload = false;
            }
            mAdapter.notifyDataSetChanged();
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_file, null);
        ButterKnife.bind(this, view);
        lvList.setOnItemClickListener(this);
        mAdapter = new DownloadFileAdapter();
        lvList.setAdapter(mAdapter);
        return view;
    }

    public void update(List<FileShowModel> list) {
        if (fileShowList == null) {
            fileShowList = new ArrayList<>();
        }
        if (fileShowList.size() > 0) {
            return;
        }
        fileShowList.clear();
        fileShowList.addAll(list);
        mAdapter.notifyDataSetChanged();
        startDownload();
    }

    private void startDownload() {
        if (downLoadList == null) {
            downLoadList = new ArrayList<>();
        }
        if (hasDownLoadList == null) {
            hasDownLoadList = new ArrayList<>();
        }
        downLoadList.addAll(fileShowList);
        new Thread() {
            @Override
            public void run() {
                super.run();
                isDownload = true;
                while (isDownload) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileShowModel fileShowModel = mAdapter.getItem(position);
        String path = FileUtil.getExternalDir().getPath() + "/" + fileShowModel.dirName + "/" + fileShowModel.fileName;
        if (fileShowModel.isDownload) {
            try {
                if (fileShowModel.fileName.endsWith("doc")) {
                    startActivity(FileUtil.getWordFileIntent(path));
                }
                if (fileShowModel.fileName.endsWith("pdf")) {
                    startActivity(FileUtil.getPdfFileIntent(path));
                }
                if (fileShowModel.fileName.endsWith("xls")) {
                    startActivity(FileUtil.getExcelFileIntent(path));
                }
                if (fileShowModel.fileName.endsWith("jpg")) {
                    CustomPop customPop = new CustomPop(getActivity(), CustomPop.Type.Type_Img);
                    customPop.setImageResource(R.drawable.home_viewpager_one);
                    customPop.show();
                }
                fileShowModel.isRead = true;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                fileShowModel.readTime = sdf.format(new Date());
                DbManager.getInstace(getContext()).updateFile(fileShowModel);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "请安装软件打开文件。", Toast.LENGTH_SHORT).show();
            }
            mAdapter.notifyDataSetChanged();
        } else {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_clear)
    @Override
    public void onClick(View v) {
        if (fileShowList == null || fileShowList.size() == 0) {
            return;
        }
        for (int i = 0; i < fileShowList.size(); i++) {
            if (fileShowList.get(i).isDownload) {
                fileShowList.get(i).isShow = false;
                DbManager.getInstace(getContext()).updateFile(fileShowList.get(i));
                fileShowList.remove(i);
                i--;
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    class DownloadFileAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (fileShowList == null) {
                return 0;
            }
            return fileShowList.size();
        }

        @Override
        public FileShowModel getItem(int position) {
            return fileShowList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_file_down_new, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            FileShowModel fileShowModel = getItem(position);
            holder = (ViewHolder) convertView.getTag();
            holder.txtFileName.setText(fileShowModel.fileName);
            holder.txtFileDir.setText("文件目录：" + fileShowModel.dirName);

            if (!fileShowModel.isDownload) {
                holder.txtStatus.setText("未更新");
            }
            if (fileShowModel.progress > 0 && fileShowModel.progress < 100) {
                holder.txtStatus.setText(fileShowModel.progress + "%");
            }
            if (fileShowModel.progress >= 100) {
                holder.txtStatus.setText("直接打开");
            }
            if (fileShowModel.isRead) {
                holder.txtStatus.setText(fileShowModel.readTime + "  已阅");
                holder.txtStatus.setTextColor(getContext().getResources().getColor(R.color.textGrey));
                holder.txtFileName.setTextColor(getContext().getResources().getColor(R.color.textGrey));
                holder.txtFileDir.setTextColor(getContext().getResources().getColor(R.color.textGrey));
            } else {
                holder.txtStatus.setTextColor(getContext().getResources().getColor(R.color.textBlack));
                holder.txtFileName.setTextColor(getContext().getResources().getColor(R.color.textBlack));
                holder.txtFileDir.setTextColor(getContext().getResources().getColor(R.color.textBlack));
            }

            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.txt_file_name)
            TextView txtFileName;
            @Bind(R.id.txt_file_dir)
            TextView txtFileDir;
            @Bind(R.id.txt_status)
            TextView txtStatus;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
