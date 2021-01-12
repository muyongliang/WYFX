package com.wyfx.upms.service.impl;

import com.wyfx.upms.model.Function;
import com.wyfx.upms.model.Role;
import com.wyfx.upms.model.User;
import com.wyfx.upms.model.vo.FunctionVo;
import com.wyfx.upms.repository.FunctionRepo;
import com.wyfx.upms.repository.RoleRepo;
import com.wyfx.upms.repository.UserRepo;
import com.wyfx.upms.service.FunctionService;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.ModelChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: liuxingyu
 * DATE: 2017-07-04.9:51
 * description:
 * version:
 */
@Service
@Transactional
public class FunctionSrvImpl implements FunctionService {
    @Autowired
    private FunctionRepo functionRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public List<FunctionVo> getMenuByLoginUser(User loginUser) throws Exception {
        User user = userRepo.findById((loginUser.getId()));
        Role role = user.getRole();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        List<Function> functions = functionRepo.findByRolesAndRank(roles, Constants.ONE_MENU);
        List<FunctionVo> functionVos = ModelChange.changeList(FunctionVo.class, functions);
        return functionVos;
    }

    @Override
    public boolean verifyFunction(String username, String path) {
        User user = userRepo.findByUsername(username);
        Role role = user.getRole();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        List<Function> functions = functionRepo.findByRoles(roles);
        for (Function function : functions) {
            if (path != null && path.equals(function.getPage()))
                return true;
        }
        return false;
    }

    @Override
    public List<FunctionVo> getMenuBySystem(User loginUser, String code) throws Exception {
        User user = userRepo.findById((loginUser.getId()));
        Role role = user.getRole();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        Function function = functionRepo.findByCode(code);
        List<Function> functions = functionRepo.findByRolesAndParentFunction(roles, function);
        List<FunctionVo> functionVos = ModelChange.changeList(FunctionVo.class, functions);
        return functionVos;
    }
}
