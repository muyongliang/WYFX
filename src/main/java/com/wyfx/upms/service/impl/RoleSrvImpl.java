package com.wyfx.upms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wyfx.upms.model.Role;
import com.wyfx.upms.model.vo.RoleVo;
import com.wyfx.upms.repository.RoleRepo;
import com.wyfx.upms.service.RoleService;
import com.wyfx.upms.utils.ModelChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-07.9:36
 * description:
 * version:
 */
@Service
@Transactional
public class RoleSrvImpl implements RoleService {
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public List<RoleVo> getAllRole() throws Exception {
        List<Role> roles = roleRepo.findAll();
        List<RoleVo> roleVos = ModelChange.changeList(RoleVo.class, roles);
        return roleVos;
    }

    @Override
    public void addRole(Role role) {
        role.setTime(new Date());
        roleRepo.save(role);
    }

    @Override
    public void delRole(String roleIds) {
        JSONArray jsonArray = JSONArray.parseArray(roleIds);
        for (int i = 0; i < jsonArray.size(); i++) {
            Long id = (Long) jsonArray.get(i);


        }
    }
}
