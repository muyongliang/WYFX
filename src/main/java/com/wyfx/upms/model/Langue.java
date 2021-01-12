package com.wyfx.upms.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: liuxingyu
 * DATE: 2017-06-26.15:22
 * description:语言
 * version:
 */
@Entity
@Table(name = "langue")
public class Langue extends BaseModel {
    /**
     * 名称
     */
    private String name;
    /**
     * 语言类型
     */
    private Integer type;
    /**
     * 是否是菜单 1,是 0,不是
     */
    private Integer isMenu;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

}
