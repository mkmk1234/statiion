package com.kun.station.model;

/**
 * Created by kun on 16/6/15.
 */
public class NoticeModel {

    /**
     * id : 0
     * title :
     * description :
     * detail :
     */

    public int id;
    public String title;
    public String description;
    public String detail;

    public NoticeModel(int id, String description, String title, String detail) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.detail = detail;
    }
}
