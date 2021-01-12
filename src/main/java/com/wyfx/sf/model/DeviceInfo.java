package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/10/24.
 */
@Table
@Entity(name = "device_info")
public class DeviceInfo extends BaseModel {
    private Long deviceId;

    @Column(columnDefinition = "text")
    private String desciption;

    @Column(columnDefinition = "text")
    private String value;

    private Long ptime;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getPtime() {
        return ptime;
    }

    public void setPtime(Long ptime) {
        this.ptime = ptime;
    }
}
