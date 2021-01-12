package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/12.
 * 设备表
 */
@Entity
@Table(name = "device")
public class Device extends BaseModel {
    /**
     * IMEI码
     */
    private String imei;
    /**
     * 时间
     */
    private Long time;
    /**
     * 设备名称
     */
    @Column(columnDefinition = "text")
    private String name;
    /**
     * 号码
     */
    @Column(columnDefinition = "text")
    private String number;
    /**
     * 备注
     */
    @Column(columnDefinition = "text")
    private String note;
    /**
     * 容量
     */
    @Column(columnDefinition = "text")
    private String count;
    /**
     * 状态  在线状态:0:在线;-1:离线；1：设备删除状态  2,全部
     */
    @Column(columnDefinition = "int default 0")
    private int status;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
