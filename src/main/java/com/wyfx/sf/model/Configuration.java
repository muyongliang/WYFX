package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by liu on 2017/9/25.
 * 硬盘配置
 */
@Entity
@Table(name = "configuration")
public class Configuration extends BaseModel {
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * URL
     */
    private String url1 = "";
    private String url2 = "";
    private String url3 = "";
    /**
     * 基站 1，开 0，关
     */
    @Column(columnDefinition = "tinyint default 0")
    private Integer baseStation = 0;
    /**
     * 基站名
     */
    private String baseStationName = "";

    /**
     * WiFi 1，开 0，关
     */
    @Column(columnDefinition = "tinyint default 0")
    private Integer wifi = 0;
    /**
     * WiFi名称
     */
    private String wifiName = "";
    /**
     * WiFi密码
     */
    private String wifiPassword = "";
    /**
     * 上线等待时间 单位：分钟
     */
    private int onlineWaitTime = 0;
    /**
     * 休眠周期时间 单位：分钟
     */
    private int dormantTime = 0;

    /**
     * apk版本
     */
    private String apkVersion = "";

    /**
     * apk安装时间
     */
    private Date apkTime;

    /**
     * apk更新状态 -1,待更新 0，更新中，1，更新完成
     */
    private Integer status = -1;

    /**
     * 待更新版本
     */
    private String stayApkVersion = "";

    /**
     * 远程操作状态
     */
    private Integer remoteStatus = 1;
    /**
     * 不扫描文件目录
     */
    private String path;

    /**
     * 扫描状态 1.开，扫描 0，关
     */
    private Integer scanStatus = 1;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(Integer scanStatus) {
        this.scanStatus = scanStatus;
    }

    public Integer getRemoteStatus() {
        return remoteStatus;
    }

    public void setRemoteStatus(Integer remoteStatus) {
        this.remoteStatus = remoteStatus;
    }

    public String getStayApkVersion() {
        return stayApkVersion;
    }

    public void setStayApkVersion(String stayApkVersion) {
        this.stayApkVersion = stayApkVersion;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBaseStationName() {
        return baseStationName;
    }

    public void setBaseStationName(String baseStationName) {
        this.baseStationName = baseStationName;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public Date getApkTime() {
        return apkTime;
    }

    public void setApkTime(Date apkTime) {
        this.apkTime = apkTime;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public Integer getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(Integer baseStation) {
        this.baseStation = baseStation;
    }

    public Integer getWifi() {
        return wifi;
    }

    public void setWifi(Integer wifi) {
        this.wifi = wifi;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public int getOnlineWaitTime() {
        return onlineWaitTime;
    }

    public void setOnlineWaitTime(int onlineWaitTime) {
        this.onlineWaitTime = onlineWaitTime;
    }

    public int getDormantTime() {
        return dormantTime;
    }

    public void setDormantTime(int dormantTime) {
        this.dormantTime = dormantTime;
    }
}


