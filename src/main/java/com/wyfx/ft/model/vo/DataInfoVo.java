package com.wyfx.ft.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by liu on 2017/8/9.
 */
public class DataInfoVo extends BaseVo {

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataInfoVo that = (DataInfoVo) o;

        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (userAgent != null ? !userAgent.equals(that.userAgent) : that.userAgent != null) return false;
        if (browser != null ? !browser.equals(that.browser) : that.browser != null) return false;
        if (browserVersion != null ? !browserVersion.equals(that.browserVersion) : that.browserVersion != null)
            return false;
        if (system != null ? !system.equals(that.system) : that.system != null) return false;
        if (systemVersion != null ? !systemVersion.equals(that.systemVersion) : that.systemVersion != null)
            return false;
        if (device != null ? !device.equals(that.device) : that.device != null) return false;
        if (isp != null ? !isp.equals(that.isp) : that.isp != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        return city != null ? city.equals(that.city) : that.city == null;
    }

    @Override
    public int hashCode() {
        int result = time != null ? time.hashCode() : 0;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
        result = 31 * result + (browser != null ? browser.hashCode() : 0);
        result = 31 * result + (browserVersion != null ? browserVersion.hashCode() : 0);
        result = 31 * result + (system != null ? system.hashCode() : 0);
        result = 31 * result + (systemVersion != null ? systemVersion.hashCode() : 0);
        result = 31 * result + (device != null ? device.hashCode() : 0);
        result = 31 * result + (isp != null ? isp.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}
