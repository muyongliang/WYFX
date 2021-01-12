package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/7.
 * 信号
 */
@Entity
@Table(name = "node_info")
public class NodeInfo extends BaseModel {
    /**
     * 网络供应商
     */
    @Column(columnDefinition = "text")
    private String netprovider;
    /**
     * 网络类型
     */
    @Column(columnDefinition = "text")
    private String networktype;
    /**
     * 信号强度
     */
    private int netdbm;
    /**
     * 时间
     */
    private Long ptime;

    private Long deviceId;

    public String getNetprovider() {
        return netprovider;
    }

    public void setNetprovider(String netprovider) {
        this.netprovider = netprovider;
    }

    public String getNetworktype() {
        return networktype;
    }

    public void setNetworktype(String networktype) {
        this.networktype = networktype;
    }

    public int getNetdbm() {
        return netdbm;
    }

    public void setNetdbm(int netdbm) {
        this.netdbm = netdbm;
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
