package com.wyfx.ft.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by liu on 2017/8/18.
 */
@Entity
@Table(name = "fw_file_info")
public class FwFileInfo extends BaseModel {
    /**
     * 文件名
     */
    private String filename;
    /**
     * 文件格式
     */
    private String swf;
    /**
     * 网络协议
     */
    private String network;
    /**
     * 外传时间
     */
    private Date time;

    private Long longTime;
    /**
     * 发送设备ip
     */
    private String ip;
    /**
     * 设备名称
     */
    private String device;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 详情
     */
    private String details;
    /**
     * 操作
     */
    /**
     * 文件路径
     */
    private String path;

    private String uuid;
    /**
     * 修改状态  1，已修改  0，未修改
     */
    private Integer updateStatus;


    @OneToOne
    private FwType fwType;

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

    public FwType getFwType() {
        return fwType;
    }

    public void setFwType(FwType fwType) {
        this.fwType = fwType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSwf() {
        return swf;
    }

    public void setSwf(String swf) {
        this.swf = swf;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

