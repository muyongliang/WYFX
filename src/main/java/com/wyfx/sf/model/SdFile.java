package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/12.
 */
@Entity
@Table(name = "sd_file")
public class SdFile extends BaseModel {
    private Long deviceId;
    /**
     * 文件类型
     */
    private int type;
    /**
     * 文件名
     */
    @Column(columnDefinition = "text")
    private String filename;
    /**
     * 路径
     */
    @Column(columnDefinition = "text")
    private String path;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件二进制
     */
    @Column(columnDefinition = "tinyblob")
    private byte[] file;

    private Long time;

    private Long ptime;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getPtime() {
        return ptime;
    }

    public void setPtime(Long ptime) {
        this.ptime = ptime;
    }
}
