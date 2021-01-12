package com.wyfx.upms.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: liuxingyu
 * DATE: 2017-06-26.14:24
 * description:角色表
 * version:
 */
@Entity
@Table(name = "auth_role")
public class Role extends BaseModel {
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
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "role")
    private Set<User> users = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "role_function", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "function_id", referencedColumnName = "id")})
    private Set<Function> functions = new HashSet<>();

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<Function> functions) {
        this.functions = functions;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
