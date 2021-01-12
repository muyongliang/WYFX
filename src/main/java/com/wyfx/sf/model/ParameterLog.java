package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/12.
 */
@Entity
@Table(name = "parameter_log")
public class ParameterLog extends BaseModel {
    private Long ptime;

    private int runType;

    private int dataType;
    /**
     * 命令类型
     */
    private int downcommand;
    /**
     * 描述
     */
    private String description;

    private Long deviceId;
    @Column(columnDefinition = "text")
    private String args;

    private Long etime;

    private String pDate;

    private String eDate;
    /**
     * 文件目录
     */
    private String path;

    /**
     * 文件名
     */
    private String filename;

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
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

    public Long getEtime() {
        return etime;
    }

    public void setEtime(Long etime) {
        this.etime = etime;
    }

    public Long getPtime() {
        return ptime;
    }

    public void setPtime(Long ptime) {
        this.ptime = ptime;
    }

    public int getRunType() {
        return runType;
    }

    public void setRunType(int runType) {
        this.runType = runType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getDowncommand() {
        return downcommand;
    }

    public void setDowncommand(int downcommand) {
        this.downcommand = downcommand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}
