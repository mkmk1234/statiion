package com.kun.station.response;

import com.kun.station.model.DirectoryModel;
import com.kun.station.model.MenuItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/6/18.
 */
public class MenuItemResponse {
    private List<DirectoryModel> dirList;
    private List<MenuItemModel> menuList;

    public List<DirectoryModel> getDirList() {
        if (dirList == null) {
            dirList = new ArrayList<>();
        }
        return dirList;
    }

    public void setDirList(List<DirectoryModel> dirList) {
        this.dirList = dirList;
    }

    public List<MenuItemModel> getMenuList() {
        if (menuList == null) {
            menuList = new ArrayList<>();
        }
        return menuList;
    }

    public void setMenuList(List<MenuItemModel> menuList) {
        this.menuList = menuList;
    }
}
