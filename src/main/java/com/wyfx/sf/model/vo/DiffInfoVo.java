package com.wyfx.sf.model.vo;


/**
 * Created by liu on 2017/9/18.
 */
public class DiffInfoVo extends BaseVo {
    private Long deviceId;
    /**
     * 文件修改全路径
     */
    private String path;
    /**
     * 修改方式 ： 1，添加 2，修改  3，删除
     */
    private Integer type;

    private String time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
