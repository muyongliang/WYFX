package com.wyfx.upms.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: liuxingyu
 * DATE: 2017-06-26.14:13
 * description:权限表
 * version:
 */
@Entity
@Table(name = "auth_function")
public class Function extends BaseModel {
    /**
     * 当前权限的上级权限
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "pid")
    private Function parentFunction;
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
     * 菜单结构
     */
    private int rank;
    /**
     * 添加时间
     */
    private Date time;
    /**
     * 当前权限的子级权限
     */
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "parentFunction")
    private Set<Function> children = new HashSet<>();
    @ManyToMany(mappedBy = "functions")
    private Set<Role> roles = new HashSet<>();

    public Function getParentFunction() {
        return parentFunction;
    }

    public void setParentFunction(Function parentFunction) {
        this.parentFunction = parentFunction;
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

    public Set<Function> getChildren() {
        return children;
    }

    public void setChildren(Set<Function> children) {
        this.children = children;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
