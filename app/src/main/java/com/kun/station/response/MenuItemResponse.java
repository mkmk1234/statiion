package com.kun.station.response;

import com.kun.station.model.FileModel;

import java.util.ArrayList;

/**
 * Created by admin on 2016/6/18.
 */
public class MenuItemResponse {
    public long id;
    public String icon;
    public String title;
    public int type;
    public ArrayList<FileModel> fileList;
}
