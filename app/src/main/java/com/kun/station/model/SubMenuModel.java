package com.kun.station.model;

import net.tsz.afinal.annotation.sqlite.Id;

import java.io.Serializable;

/**
 * Created by kun on 16/7/9.
 */
public class SubMenuModel implements Serializable {


    /**
     * icon :
     * id : 13
     * teamConcept : 555555cccccc
     * title : 车间班组文化理念
     * type : 101
     */
    @Id(column = "id")

    private int id;
    private String icon;
    private String teamConcept;
    private String title;
    private int type;
    private int menuId;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamConcept(String teamConcept) {
        this.teamConcept = teamConcept;
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

    public String getTeamConcept() {
        return teamConcept;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
