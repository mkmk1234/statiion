package com.kun.station.model;

import net.tsz.afinal.annotation.sqlite.Id;

import java.io.Serializable;

/**
 * Created by kun on 16/7/9.
 */
public class MenuModel implements Serializable {


    /**
     * icon :
     * id : 1
     * subMenuList : [""]
     * title : 系统首页
     * type : 0
     */
    @Id(column = "id")
    private int id;
    private String icon;
    private String title;
    private int type;

    public MenuModel() {
    }

    public MenuModel(int id, String icon, String title, int type) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.type = type;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

}
