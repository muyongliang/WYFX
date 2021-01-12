package com.wyfx.cs.model;

import javax.persistence.*;

/**
 * Created by liu on 2017/12/13.
 * 分析报表
 */
@Entity
@Table(name = "cs_issues")
public class Issues extends BaseModel {
    /**
     * 漏洞名称
     */
    private String message;
    /**
     * 危险级别
     */
    private String severity;
    /**
     * 文件
     */
    private String component;
    /**
     * 类型
     */
    private String type;
    /**
     * 所在行数
     */
    private int line;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "scanList_id")
    private ScanList scanList;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ScanList getScanList() {
        return scanList;
    }

    public void setScanList(ScanList scanList) {
        this.scanList = scanList;
    }
}

