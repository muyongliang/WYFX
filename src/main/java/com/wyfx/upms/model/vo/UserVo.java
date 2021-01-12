package com.wyfx.upms.model.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Author: liuxingyu
 * DATE: 2017-06-26.14:12
 * description:
 * version:
 */
public class UserVo extends BaseVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别  1，男  2，女
     */
    private Integer gender;
    /**
     * 生日
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 添加时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    /**
     * 语言
     */
    private String langue;
    private RoleVo roleVo = new RoleVo();

    public RoleVo getRoleVo() {
        return roleVo;
    }

    public void setRoleVo(RoleVo roleVo) {
        this.roleVo = roleVo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }
}
