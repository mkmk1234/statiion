package com.kun.station.model;

import java.io.Serializable;

/**
 * Created by kun on 16/7/9.
 */
public class HomeBannerModel implements Serializable {

    /**
     * id : 0
     * imageUrl :
     */

    private int id;
    private String imageUrl;

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
