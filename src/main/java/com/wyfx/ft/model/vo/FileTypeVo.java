package com.wyfx.ft.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Author: liuxingyu
 * DATE: 2017-07-19.14:35
 * description:
 * version:
 */
public class FileTypeVo extends BaseVo {
    /**
     * 文件类型
     */
    private String type;

    /**
     * 是否使用 1,是 0 ，否
     */
    private Integer status;

    /**
     * 格式
     */
    private String swf;

    /**
     * 算法导入时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date time;
    /**
     * 描述
     */
    private String description;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否支持标记 1，支持 0，不支持
     */
    private Integer sign;
    /**
     * 是否支持水印 1，支持 0，不支持
     */
    private Integer watermark;
    /**
     * 算法版本
     */
    private Double version;

    private String versionS = String.valueOf(version);

    public String getVersionS() {
        return String.valueOf(version);
    }

    public void setVersionS(String versionS) {
        this.versionS = versionS;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getWatermark() {
        return watermark;
    }

    public void setWatermark(Integer watermark) {
        this.watermark = watermark;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getSwf() {
        return swf;
    }

    public void setSwf(String swf) {
        this.swf = swf;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
