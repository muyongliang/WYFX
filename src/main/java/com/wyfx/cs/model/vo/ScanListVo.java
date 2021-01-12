package com.wyfx.cs.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by liu on 2017/12/7.
 */
public class ScanListVo extends BaseVo {
    /**
     * 发起时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;
    /**
     * 扫描状态，0，待扫描 1，扫描中 2，已扫描
     */
    private Integer status;
    /**
     * 是否静态扫描 1，是 0，否
     */
    private Integer staticStatus;

    /**
     * 是否动态扫描 1，是 0，否
     */
    private Integer dynamicStatus;
    /**
     * 危险级别
     */
    private String dangerousLevels;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 团队名称
     */
    private String teamName;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStaticStatus() {
        return staticStatus;
    }

    public void setStaticStatus(Integer staticStatus) {
        this.staticStatus = staticStatus;
    }

    public Integer getDynamicStatus() {
        return dynamicStatus;
    }

    public void setDynamicStatus(Integer dynamicStatus) {
        this.dynamicStatus = dynamicStatus;
    }

    public String getDangerousLevels() {
        return dangerousLevels;
    }

    public void setDangerousLevels(String dangerousLevels) {
        this.dangerousLevels = dangerousLevels;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
