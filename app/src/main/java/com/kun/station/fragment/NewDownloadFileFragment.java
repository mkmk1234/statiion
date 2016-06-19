package com.kun.station.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.kun.station.MyApplication;
import com.kun.station.R;
import com.kun.station.base.BaseFragment;
import com.kun.station.model.FileModel;
import com.kun.station.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kun on 16/6/19.
 */
public class NewDownloadFileFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ArrayList<FileModel> fileList;
    private Context mContext;
    private DownloadFileAdapter mAdapter;
    private List<FileModel> selectedFile = new ArrayList<>();
    private Button btn_download;
    boolean isDownload;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (int i = 0; i < selectedFile.size(); i++) {
                selectedFile.get(i).prograss += 5;
                if (selectedFile.get(i).prograss >= 100) {
                    selectedFile.get(i).prograss = 100;
                    File dir = FileUtil.getExternalDir();
                    File dirFile1 = new File(dir, selectedFile.get(i).dirName);
                    String filename = selectedFile.get(i).fileName;
                    selectedFile.remove(i);
                    try {
                        new File(dirFile1, filename).createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (selectedFile.size() == 0) {
                isDownload = false;
                btn_download.setText("下载");
            } else {
                btn_download.setText("下载中");
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        fileList = MyApplication.mGson.fromJson(FileUtil.loadRawString(mContext, R.raw.localdata_list), new TypeToken<ArrayList<FileModel>>() {
        }.getType());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_file, null);
        ListView listView = (ListView) view.findViewById(R.id.lv_list);
        btn_download = (Button) view.findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDownload) {
                    startDownload();
                }
            }
        });
        listView.setOnItemClickListener(this);
        mAdapter = new DownloadFileAdapter();
        listView.setAdapter(mAdapter);
        return view;
    }

    private void startDownload() {
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

    }

    class DownloadFileAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return fileList.size();
        }

        @Override
        public FileModel getItem(int position) {
            return fileList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_file_down_new, parent, false);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            }
            holder = (Holder) convertView.getTag();
            Log.i("sss", "" + getItem(position).isChecked + "" + position);
            holder.name.setText(getItem(position).fileName);
            holder.description.setText("文件更新2016/06/20");
            holder.version.setText("文件版本：v1.0");
            holder.dir.setText("文件目录：" + getItem(position).dirName);
            if (getItem(position).prograss <= 0) {
                holder.pb.setVisibility(View.GONE);
            } else {
                holder.pb.setVisibility(View.VISIBLE);
            }
            if (getItem(position).prograss >= 100) {
                holder.pb.setText("直接打开");
            } else {
                holder.pb.setText(getItem(position).prograss + "%");
            }
            final Holder finalHolder = holder;
            holder.pb.setTag(position);
            holder.pb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalHolder.pb.getText().toString().equals("直接打开")) {
                        String path = FileUtil.getExternalDir().getPath() + "/" + getItem((Integer) finalHolder.pb.getTag()).dirName + "/" + getItem((Integer) finalHolder.pb.getTag()).fileName;
                        try {
                            if (getItem((Integer) finalHolder.pb.getTag()).fileName.endsWith("doc")) {
                                startActivity(FileUtil.getWordFileIntent(path));
                            }
                            if (getItem((Integer) finalHolder.pb.getTag()).fileName.endsWith("pdf")) {
                                startActivity(FileUtil.getPdfFileIntent(path));
                            }
                            if (getItem((Integer) finalHolder.pb.getTag()).fileName.endsWith("xls")) {
                                startActivity(FileUtil.getExcelFileIntent(path));
                            }
                            if (getItem((Integer) finalHolder.pb.getTag()).fileName.endsWith("jpg")) {
                                startActivity(FileUtil.getImageFileIntent(path));
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "请安装软件打开文件。", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            holder.cb.setTag(position);
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selectedFile.add(getItem((Integer) finalHolder.cb.getTag()));
                    } else {
                        selectedFile.remove(getItem((Integer) finalHolder.cb.getTag()));
                    }
                    getItem((Integer) finalHolder.cb.getTag()).isChecked = isChecked;
                }
            });
            holder.cb.setChecked(getItem(position).isChecked);
            return convertView;
        }

        class Holder {
            public TextView name;
            public TextView description;
            public TextView version;
            public TextView dir;
            public TextView pb;
            public CheckBox cb;

            public Holder(View convertView) {
                name = (TextView) convertView.findViewById(R.id.txt_file_name);
                description = (TextView) convertView.findViewById(R.id.txt_file_description);
                version = (TextView) convertView.findViewById(R.id.txt_file_vesion);
                dir = (TextView) convertView.findViewById(R.id.txt_file_dir);
                pb = (TextView) convertView.findViewById(R.id.txt_prograss);
                cb = (CheckBox) convertView.findViewById(R.id.cb_checked);
            }
        }

    }
}
