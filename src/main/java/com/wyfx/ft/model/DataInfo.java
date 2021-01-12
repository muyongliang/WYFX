package com.wyfx.ft.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by www on 2016/8/26.
 */
@Entity
@Table(name = "tfs_data_info")
public class DataInfo extends BaseModel {

    /**
     * 添加时间
     */
    private Date time;
    /**
     * ip信息
     */
    private String ip;
    /**
     * 请求头信息
     */
    private String userAgent;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 浏览器版本
     */
    private String browserVersion;
    /**
     * 系统
     */
    private String system;
    /**
     * 系统版本
     */
    private String systemVersion;
    private String device;
    /**
     * isp
     */
    private String isp;
    /**
     * 国家
     */
    private String country;
    /**
     * 地区
     */
    private String region;
    /**
     * 城市
     */
    private String city;

    private Long longTime;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "file_info_id")
    private FileInfo fileInfo;

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        this.longTime = time.getTime();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
}
