package com.wyfx.upms.model.vo;

import java.util.Date;

/**
 * Author: liuxingyu
 * DATE: 2017-07-06.17:51
 * description:
 * version:
 */
public class RoleVo extends BaseVo {
    /**
     * 角色名称
     */
    private String name;
    /**
     * 编号
     */
    private int number;
    /**
     * 关键字
     */
    private String code;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 添加时间
     */
    private Date time;
    /**
     * 标识路径
     */
    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
