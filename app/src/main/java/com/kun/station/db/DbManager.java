package com.kun.station.db;

import android.content.Context;

import com.kun.station.model.FileShowModel;
import com.kun.station.model.NoticeModel;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/6/3.
 */
public class DbManager {
    private static DbManager dbManager;
    FinalDb db;

    private DbManager(Context context) {
        if (db == null) {
            db = FinalDb.create(context, "afinal_file.db");
        }
    }

    public static DbManager getInstace(Context context) {
        if (dbManager == null) {
            dbManager = new DbManager(context);
        }
        return dbManager;
    }


    public void insertNotice(NoticeModel noticeModel) {
        db.save(noticeModel);
    }

    public void updateNotice(NoticeModel noticeModel) {
        db.update(noticeModel);
    }

    public List<NoticeModel> getNoitce() {
        return db.findAll(NoticeModel.class);
    }

    public void insertFile(FileShowModel fileShowModel) {
        if (isHas(fileShowModel.dirName, fileShowModel.fileName)) {
            db.update(fileShowModel);
        } else {
            db.save(fileShowModel);
        }
    }
    public void deleteFile(FileShowModel fileShowModel) {
        db.delete(fileShowModel);
    }


    public boolean isHas(String filePath, String fileName) {
        List<FileShowModel> list = db.findAllByWhere(FileShowModel.class, " fileName=\"" + fileName + "\"" + " and dirName=\"" + filePath + "\"");
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isDownload(String filePath, String fileName) {
        List<FileShowModel> list = db.findAllByWhere(FileShowModel.class, " fileName=\"" + fileName + "\"" + " and dirName=\"" + filePath + "\"" + " and isDownload=\"" + 1 + "\"");
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isStore(String filePath, String fileName) {
        List<FileShowModel> list = db.findAllByWhere(FileShowModel.class, " fileName=\"" + fileName + "\"" + " and dirName=\"" + filePath + "\"" + " and isStore=\"" + 1 + "\"");
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public List<FileShowModel> getShowFiles() {
        List<FileShowModel> list = db.findAllByWhere(FileShowModel.class, " isShow=\"" + 1 + "\"");
        if (list != null && list.size() > 0) {
            return list;
        }
        return new ArrayList<>();
    }

    public List<FileShowModel> getStoreFiles() {
        List<FileShowModel> list = db.findAllByWhere(FileShowModel.class, " isStore=\"" + 1 + "\"");
        if (list != null && list.size() > 0) {
            return list;
        }
        return new ArrayList<>();
    }

    public List<FileShowModel> getFile(String filePath, String fileName) {
        List<FileShowModel> list = db.findAllByWhere(FileShowModel.class, " fileName=\"" + fileName + "\"" + " and dirName=\"" + filePath + "\"");
        if (list != null && list.size() > 0) {
            return list;
        }
        return new ArrayList<>();
    }

    public void updateFile(FileShowModel fileShowModel) {
        db.update(fileShowModel);
    }

    public void updateShow(FileShowModel fileShowModel) {
        updateFile(fileShowModel);
    }

    public void updateDownload(FileShowModel fileShowModel) {
        updateFile(fileShowModel);
    }

    public void updateStore(String dirName, String fileName) {
        List<FileShowModel> list = getFile(dirName, fileName);
        if (list.size() > 0) {
            list.get(0).isStore = !list.get(0).isStore;
        }
        updateFile(list.get(0));
    }

}
