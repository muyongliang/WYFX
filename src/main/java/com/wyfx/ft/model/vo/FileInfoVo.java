package com.wyfx.ft.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by liu on 2017/7/24.
 */
public class FileInfoVo extends BaseVo {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 添加时间，数值
     */
    private Long longTime;
    /**
     * 文件唯一id
     */
    private String uuid;
    /**
     * 文件描述
     */
    private String description;
    /**
     * 标签
     */
    private String tag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 防火墙识别码
     */
    private Boolean fireWallNumber;
    /**
     * 追踪标记
     */
    private Boolean sign;

    /**
     * 状态 0，未标记  1，已标记
     */
    private Integer status = 0;

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getFireWallNumber() {
        return fireWallNumber;
    }

    public void setFireWallNumber(Boolean fireWallNumber) {
        this.fireWallNumber = fireWallNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }
}
