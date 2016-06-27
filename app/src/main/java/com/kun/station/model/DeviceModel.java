package com.kun.station.model;

/**
 * Created by kun on 16/6/27.
 */
public class DeviceModel {

    /**
     * id :
     * deptName :
     * station :
     */

    private String id;
    private String deptName;
    private String station;

    public void setId(String id) {
        this.id = id;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getId() {
        return id;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getStation() {
        return station;
    }
}
