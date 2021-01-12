package com.wyfx.upms.model.vo;

import java.util.Date;

/**
 * Author: liuxingyu
 * DATE: 2017-07-06.9:17
 * description:
 * version:
 */
public class FunctionVo extends BaseVo {
    /**
     * 权限名称
     */
    private String name;
    /**
     * 关键字
     */
    private String code;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 权限对应的访问url地址
     */
    private String page;
    /**
     * 当前权限是否生成到菜单,1表示生成，0表示不生成
     */
    private Integer generatemenu;
    /**
     * 菜单顺序
     */
    private int rank;
    /**
     * 添加时间
     */
    private Date time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getGeneratemenu() {
        return generatemenu;
    }

    public void setGeneratemenu(Integer generatemenu) {
        this.generatemenu = generatemenu;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
