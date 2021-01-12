package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/7.
 * 电池实体
 */
@Entity
@Table(name = "battery_info")
public class BatteryInfo extends BaseModel {
    /**
     * 电压
     */
    private int voltage;
    /**
     * 电量水平
     */
    @Column(columnDefinition = "text")
    private String health;
    /**
     * 电量占比
     */
    private int level;
    /**
     * 电量总量
     */
    private int scale;
    /**
     * 充电方式
     */
    @Column(columnDefinition = "text")
    private String pluged;
    /**
     * 状态 正在充电，充满
     */
    @Column(columnDefinition = "text")
    private String status;
    /**
     * 电池工艺（种类）
     */
    @Column(columnDefinition = "text")
    private String technology;
    /**
     * 温度
     */
    private int temperature;
    /**
     * 时间
     */
    private Long ptime;

    private Long deviceId;

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getPluged() {
        return pluged;
    }

    public void setPluged(String pluged) {
        this.pluged = pluged;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
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
}
