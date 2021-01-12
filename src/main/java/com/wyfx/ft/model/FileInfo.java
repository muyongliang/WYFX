package com.wyfx.ft.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.15:10
 * description:文件
 * version:
 */
@Entity
@Table(name = "tfs_file_info")
public class FileInfo extends BaseModel {
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
     * 状态 0，未标记  1，已标记 3,格式错误 2 失敗
     */
    private Integer status = 0;
    /**
     * 标记城市
     */
    private String signCity;
    /**
     * md5 id
     */
    private String md5Id;


    @OneToOne
    private Type type;
    @OneToOne
    private FileType fileType;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "fileInfo")
    private Set<DataInfo> dataInfos = new HashSet<>();

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public String getMd5Id() {
        return md5Id;
    }

    public void setMd5Id(String md5Id) {
        this.md5Id = md5Id;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getSignCity() {
        return signCity;
    }

    public void setSignCity(String signCity) {
        this.signCity = signCity;
    }

    public Type getType() {
        return type;
    }


    public void setType(Type type) {
        this.type = type;
    }

    public Set<DataInfo> getDataInfos() {
        return dataInfos;
    }

    public void setDataInfos(Set<DataInfo> dataInfos) {
        this.dataInfos = dataInfos;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        this.longTime = time.getTime();
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

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }


}
