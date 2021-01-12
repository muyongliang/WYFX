package com.wyfx.cs.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liu on 2017/12/7.
 * 扫描清单
 */
@Entity
@Table(name = "cs_scan_list")
public class ScanList extends BaseModel {
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "scanList")
    Set<Issues> issuesSet = new HashSet<>();
    /**
     * 发起时间
     */
    private Date startTime;
    private Long longTime;
    /**
     * 结束时间
     */
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
     * 危险说明
     */
    private String hazardStatements;
    /**
     * 代码行数
     */
    private String lineCode;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 发起人
     */
    private String initiator;
    /**
     * 动态扫描地址
     */
    private String dynamicScanAddress;
    /**
     * 项目密钥
     */
    private String projectKey;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "team_id")
    private Team team;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    public Set<Issues> getIssuesSet() {
        return issuesSet;
    }

    public void setIssuesSet(Set<Issues> issuesSet) {
        this.issuesSet = issuesSet;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getDynamicScanAddress() {
        return dynamicScanAddress;
    }

    public void setDynamicScanAddress(String dynamicScanAddress) {
        this.dynamicScanAddress = dynamicScanAddress;
    }

    public String getHazardStatements() {
        return hazardStatements;
    }

    public void setHazardStatements(String hazardStatements) {
        this.hazardStatements = hazardStatements;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
