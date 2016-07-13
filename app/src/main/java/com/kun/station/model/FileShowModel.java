package com.kun.station.model;

import net.tsz.afinal.annotation.sqlite.Id;

import java.io.Serializable;

/**
 * Created by admin on 2016/6/18.
 */
public class FileShowModel implements Serializable {
    @Id(column = "id")
    public int id;
    public int fileShowID;
    public int imageId;
    public String fileName;
    public String fileUrl;
    public String dirName;
    public int isShow;
    public int isDownload;
    public int isStore;
    public int isRead;
    public int progress = 0;
    public String readTime;

    public FileShowModel() {
    }

    public FileShowModel(FileModel fileModel, int imageId, int isShow, int isDownload, int isStore, int isRead, String readTime) {
        this.fileName = fileModel.fileName;
        this.fileUrl = fileModel.fileUrl;
        this.dirName = fileModel.dirName;
        this.fileShowID = fileModel.fileId;
        this.imageId = imageId;
        this.isShow = isShow;
        this.isDownload = isDownload;
        this.isStore = isStore;
        this.isRead = isRead;
        this.readTime = readTime;
    }

    public int getId() {
        return fileShowID;
    }

    public void setId(int id) {
        this.fileShowID = id;
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








    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getIsDownload() {
        return isDownload;
    }

    public void setIsDownload(int isDownload) {
        this.isDownload = isDownload;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getIsStore() {
        return isStore;
    }

    public void setIsStore(int isStore) {
        this.isStore = isStore;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public int getFileShowID() {
        return fileShowID;
    }

    public void setFileShowID(int fileShowID) {
        this.fileShowID = fileShowID;
    }
}
