package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/7.
 */
@Entity
@Table(name = "gps")
public class Gps extends BaseModel {
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 高度
     */
    private Double altitude;
    /**
     * 时间
     */
    private Long time;

    private Long ptime;


    private Long deviceId;
    @Column(columnDefinition = "int default null")
    private Integer captureType;
    @Column(columnDefinition = "int default null")
    private Integer actionType;

    private Long typeTime;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getPtime() {
        return ptime;
    }

    public void setPtime(Long ptime) {
        this.ptime = ptime;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public int getCaptureType() {
        return captureType;
    }

    public void setCaptureType(int captureType) {
        this.captureType = captureType;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public Long getTypeTime() {
        return typeTime;
    }

    public void setTypeTime(Long typeTime) {
        this.typeTime = typeTime;
    }
}
