package com.kun.station.model;

import java.util.List;

/**
 * Created by kun on 16/7/9.
 */
public class MenuItemModel {
    /**
     * icon :
     * id : 1
     * subMenuList : [""]
     * title : 系统首页
     * type : 0
     */
    private int id;
    private String icon;
    private String title;
    private int type;
    private List<SubMenuModel> subMenuList;

    public MenuItemModel() {
    }

    public MenuItemModel(MenuModel menuModel, List<SubMenuModel> subMenuList) {
        this.subMenuList = subMenuList;
        this.id = menuModel.getId();
        this.icon = menuModel.getIcon();
        this.title = menuModel.getTitle();
        this.type = menuModel.getType();
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

    public void setSubMenuList(List<SubMenuModel> subMenuList) {
        this.subMenuList = subMenuList;
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

    public List<SubMenuModel> getSubMenuList() {
        return subMenuList;
    }
}
