package com.kun.station.model;

import java.io.Serializable;

/**
 * Created by kun on 16/6/27.
 */
public class DeviceModel extends BaseModel {

    /**
     * id :
     * deptName :
     * station :
     */

    public String erialId;
    public String deptName;
    public String station;
    public String equipmentNumber;

    public DeviceModel(String equipmentNumber, String station, String deptName) {
        this.equipmentNumber = equipmentNumber;
        this.station = station;
        this.deptName = deptName;
    }

}
