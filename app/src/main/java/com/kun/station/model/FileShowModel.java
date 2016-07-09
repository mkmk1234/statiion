package com.kun.station.model;

import net.tsz.afinal.annotation.sqlite.Id;

import java.io.Serializable;

/**
 * Created by admin on 2016/6/18.
 */
public class FileShowModel implements Serializable {
    @Id(column = "id")
    public int id;
    public int imageId;
    public String fileName;
    public String fileUrl;
    public String dirName;
    public boolean isShow;
    public boolean isDownload;
    public boolean isStore;
    public boolean isRead;
    public int progress = 0;
    public String readTime;

    public FileShowModel() {
    }

    public FileShowModel(FileModel fileModel, int imageId, boolean isShow, boolean isDownload, boolean isStore, boolean isRead, String readTime) {
        this.fileName = fileModel.fileName;
        this.fileUrl = fileModel.fileUrl;
        this.dirName = fileModel.dirName;
        this.imageId = imageId;
        this.isShow = isShow;
        this.isDownload = isDownload;
        this.isStore = isStore;
        this.isRead = isRead;
        this.readTime = readTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public boolean isStore() {
        return isStore;
    }

    public void setStore(boolean store) {
        isStore = store;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }
}
