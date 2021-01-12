package com.wyfx.ft.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Author: liuxingyu
 * DATE: 2017-07-19.14:31
 * description:文件类型
 * version:
 */
@Entity
@Table(name = "tfs_file_type")
public class FileType extends BaseModel {
    /**
     * 文件类型
     */
    private String type;

    /**
     * 是否导入算法 1 ，是 0 ，否
     */
    private Integer status;

    /**
     * 类的全路径 标记
     */
    private String catalog;
    /**
     * 方法 标记
     */
    private String method;
    /**
     * 类的全路径 水印
     */
    private String catalogWatermark;
    /**
     * 方法 水印
     */
    private String methodWatermark;

    /**
     * 格式
     */
    private String swf;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 算法导入时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
    /**
     * 算法存放目录
     */
    private String path;

    private String uuid;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSwf() {
        return swf;
    }

    public void setSwf(String swf) {
        this.swf = swf;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
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

    public String getCatalogWatermark() {
        return catalogWatermark;
    }

    public void setCatalogWatermark(String catalogWatermark) {
        this.catalogWatermark = catalogWatermark;
    }

    public String getMethodWatermark() {
        return methodWatermark;
    }

    public void setMethodWatermark(String methodWatermark) {
        this.methodWatermark = methodWatermark;
    }
}
