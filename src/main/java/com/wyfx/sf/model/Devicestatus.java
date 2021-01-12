package com.wyfx.sf.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/14.
 * 设备在线，离线状态
 */
@Entity
@Table(name = "devicestatus")
public class Devicestatus extends BaseModel {
    private Long deviceId;

    /**
     * 时间
     */
    private Long time;
    /**
     * 状态 ，-1：离线 0：在线
     */
    private int status;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
