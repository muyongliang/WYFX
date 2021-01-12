package com.wyfx.sf.model.vo;

/**
 * Created by liu on 2017/9/14.
 */
public class DevicestatusVo {
    private Long deviceId;
    /**
     * 上线时间
     */
    private String onlineTime;
    /**
     * 下线时间
     */
    private String offlineTime;

    /**
     * 硬盘可控时间
     */
    private String controllTime;

    public String getControllTime() {
        return controllTime;
    }

    public void setControllTime(String controllTime) {
        this.controllTime = controllTime;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(String offlineTime) {
        this.offlineTime = offlineTime;
    }
}
