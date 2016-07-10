package com.kun.station.model;

import net.tsz.afinal.annotation.sqlite.Id;

import java.io.Serializable;

/**
 * Created by kun on 16/6/27.
 */
public class DirectoryModel implements Serializable {
    /**
     * dirName : 车站状况
     * dirPath : /车站状况
     * id : 1
     * lastTime : 1468077987000
     * status : 0
     */
    @Id(column = "id")
    private int id;
    private int dirId;
    private String dirName;
    private String dirPath;
    private String lastTime;
    private int status;

    public DirectoryModel() {
    }

    public DirectoryModel(int id, String dirName, String dirPath, String lastTime, int status) {
        this.id = id;
        this.dirName = dirName;
        this.dirPath = dirPath;
        this.lastTime = lastTime;
        this.status = status;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDirName() {
        return dirName;
    }

    public String getDirPath() {
        return dirPath;
    }

    public int getId() {
        return id;
    }

    public String getLastTime() {
        return lastTime;
    }

    public int getStatus() {
        return status;
    }

    public int getDirId() {
        return dirId;
    }

    public void setDirId(int dirId) {
        this.dirId = dirId;
    }
}
