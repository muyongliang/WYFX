package com.wyfx.ft.model;

import javax.persistence.*;

@Entity
@Table(name = "erdai_api_weather_static_heweather")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mid;

    private String city;

    private String zcity;

    private String cnty;

    private String zcnty;

    private String id;

    private String lat;

    private String lon;

    private String prov;

    public String getZcity() {
        return zcity;
    }

    public void setZcity(String zcity) {
        this.zcity = zcity;
    }

    public String getZcnty() {
        return zcnty;
    }

    public void setZcnty(String zcnty) {
        this.zcnty = zcnty;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }
}
