package com.wyfx.upms.controller;

import com.wyfx.upms.model.Role;
import com.wyfx.upms.model.vo.RoleVo;
import com.wyfx.upms.service.RoleService;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-07.9:33
 * description:角色管理
 * version:
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllRole", method = RequestMethod.GET)
    public Object getAllRole() throws Exception {
        List<RoleVo> roleVoList = roleService.getAllRole();
        return new ResponseBody(Constants.SUCCESS_CODE, roleVoList);
    }

    /**
     * 添加角色
     *
     * @param role name角色名称
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.GET)
    public Object addRole(Role role) {
        roleService.addRole(role);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    @RequestMapping(value = "delRole", method = RequestMethod.GET)
    public Object delRole(String roleIds) {
        roleService.delRole(roleIds);
        return null;
    }
}
