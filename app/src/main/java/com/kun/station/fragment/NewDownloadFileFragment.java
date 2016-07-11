package com.kun.station.fragment;

import android.os.Bundle;
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
import com.kun.station.util.Log;
import com.kun.station.widget.CustomPop;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import java.io.File;
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
    private List<FileShowModel> fileShowList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_file, null);
        ButterKnife.bind(this, view);
        initData();
        lvList.setOnItemClickListener(this);
        lvList.setAdapter(mAdapter);
        return view;
    }

    private void initData() {
        mAdapter = new DownloadFileAdapter();
        fileShowList = DbManager.getInstace(getContext()).getShowFiles();
        if (fileShowList.size() > 0) {
            startDownloadFile(0);
        }
    }

    private void startDownloadFile(final int position) {
        if (fileShowList.size() <= position) {
            return;
        }
        final FileShowModel fileShowModel = fileShowList.get(position);
        if (fileShowModel.isDownload) {
            startDownloadFile(position + 1);
            return;
        }
        FinalHttp fh = new FinalHttp();
        //调用download方法开始下载
        HttpHandler handler = fh.download("http://www.panda-e.com//public/download/pandaol.apk",
                new File(FileUtil.getExternalDir(), fileShowModel.getDirName() + fileShowModel.getFileName()).getAbsolutePath(), true,
                new AjaxCallBack() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.i("sss", "start");
                    }

                    @Override
                    public void onLoading(long count, long current) {
                        fileShowModel.progress = (int) (current * 100 / count);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }

                    @Override
                    public void onSuccess(Object o) {
                        super.onSuccess(o);
                        startDownloadFile(position + 1);
                    }
                });


        //调用stop()方法停止下载
    }

    public void update(List<FileShowModel> list) {
        if (fileShowList == null) {
            fileShowList = new ArrayList<>();
        }
        fileShowList.clear();
        fileShowList.addAll(list);
        if (fileShowList.size() > 0) {
            startDownloadFile(0);
        }
        mAdapter.notifyDataSetChanged();
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
