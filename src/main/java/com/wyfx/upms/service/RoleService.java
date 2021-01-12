package com.wyfx.upms.service;

import com.wyfx.upms.model.Role;
import com.wyfx.upms.model.vo.RoleVo;

import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-07.9:35
 * description:
 * version:
 */
public interface RoleService {
    List<RoleVo> getAllRole() throws Exception;

    void addRole(Role role);

    void delRole(String roleIds);
}
