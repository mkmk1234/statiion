package com.kun.station.model;

import net.tsz.afinal.annotation.sqlite.Id;

import java.io.Serializable;

/**
 * Created by kun on 16/6/15.
 */
public class NoticeModel implements Serializable {
    @Id(column = "id")
    public int id;
    public String title;
    public String noticeContent;
    public String readTime;
    public String lastTime;
    public boolean hasRead;

    public NoticeModel() {
    }

    public NoticeModel(int id, String readTime, String title, String noticeContent, String lastTime, boolean hasRead) {
        this.id = id;
        this.readTime = readTime;
        this.title = title;
        this.noticeContent = noticeContent;
        this.lastTime = lastTime;
        this.hasRead = hasRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
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

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
