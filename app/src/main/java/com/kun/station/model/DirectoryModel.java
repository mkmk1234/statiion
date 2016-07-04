package com.kun.station.model;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * Created by kun on 16/6/27.
 */
public class DirectoryModel {

    /**
     * id : 0
     * dirType : 0
     * dirName :
     * parentDirName :
     */
    @Id(column = "id")
    private int id;
    private int dirType;
    private String dirName;
    private String parentDirName;

    public DirectoryModel() {
    }

    public DirectoryModel(int id, int dirType, String dirName, String parentDirName) {
        this.id = id;
        this.dirType = dirType;
        this.dirName = dirName;
        this.parentDirName = parentDirName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDirType(int dirType) {
        this.dirType = dirType;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public void setParentDirName(String parentDirName) {
        this.parentDirName = parentDirName;
    }

    public int getId() {
        return id;
    }

    public int getDirType() {
        return dirType;
    }

    public String getDirName() {
        return dirName;
    }

    public String getParentDirName() {
        return parentDirName;
    }
}
