package com.wyfx.sf.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by liu on 2017/9/12.
 */
@Entity
@Table(name = "command")
public class Command extends BaseModel {
    private Long deviceId;
    /**
     * 命令类型
     */
    private int cmd;
    /**
     * 参数
     */
    private String args;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}
