package com.wyfx.sf.model.vo;

import com.wyfx.sf.utils.DateUtils;

/**
 * Created by liu on 2017/9/19.
 */
public class GpsVo extends BaseVo {
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

    private String date;

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

    public String getDate() {
        return DateUtils.stampToDate(ptime.toString());
    }

    public void setDate(String date) {
        this.date = date;
    }
}
