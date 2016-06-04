package com.kun.station.model;

/**
 * Created by kun on 16/6/4.
 */
public class Model {
    public String name;
    public int selectedImg;
    public int unSelectedImg;

    public Model(String name, int selectedImg, int unSelectedImg) {
        this.name = name;
        this.selectedImg = selectedImg;
        this.unSelectedImg = unSelectedImg;
    }
}
