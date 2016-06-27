package com.kun.station.model;

/**
 * Created by kun on 16/6/15.
 */
public class NoticeModel {

    public int id;
    public String title;
    public String detail;
    public String readTime;
    public boolean hasRead;

    public NoticeModel() {
    }

    public NoticeModel(int id, String readTime, String title, String detail, boolean hasRead) {
        this.id = id;
        this.readTime = readTime;
        this.title = title;
        this.detail = detail;
        this.hasRead = hasRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }
}
