package com.wyfx.sf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/18.
 * 文件更改记录
 */
@Entity
@Table(name = "diff_info")
public class DiffInfo extends BaseModel {
    private Long deviceId;
    /**
     * 文件修改全路径
     */
    @Column(columnDefinition = "text")
    private String path;
    /**
     * 修改方式 ： 1，添加 2，修改  3，删除
     */
    private Integer type;

    private Long ptime;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPtime() {
        return ptime;
    }

    public void setPtime(Long ptime) {
        this.ptime = ptime;
    }


}
