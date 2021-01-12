package com.wyfx.upms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wyfx.upms.model.Role;
import com.wyfx.upms.model.User;
import com.wyfx.upms.model.vo.RoleVo;
import com.wyfx.upms.model.vo.UserVo;
import com.wyfx.upms.repository.RoleRepo;
import com.wyfx.upms.repository.UserRepo;
import com.wyfx.upms.service.UserService;
import com.wyfx.upms.utils.ModelChange;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-06-30.10:15
 * description:
 * version:
 */
@Service
@Transactional
public class UserSrvImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public User getUserByIdAndPassword(Long id, String password) {
        // password = MD5Utils.md5(password);
        User user = userRepo.findByIdAndPassword(id, password);
        return user;
    }

    @Override
    public User saveNewPass(Long id, String newPass) {
        //   newPass = MD5Utils.md5(newPass);
        User user = userRepo.findById(id);
        user.setPassword(newPass);
        User saveUser = userRepo.save(user);
        return saveUser;
    }

    @Override
    public PageQuery getAllUser(int pageNo, int pageSize) throws Exception {
        Pageable page = new PageRequest(pageNo - 1, pageSize);
        Page<User> users = userRepo.findAll(page);
        List<User> content = users.getContent();
        PageQuery pageQuery = PageUtils.pagingList(UserVo.class, users, pageNo, pageSize);
        List<UserVo> userVos = pageQuery.getContent();
        for (int i = 0; i < content.size(); i++) {
            User user = content.get(i);
            Role role = user.getRole();
            if (role != null) {
                RoleVo roleVo = ModelChange.changeEntity(RoleVo.class, role);
                userVos.get(i).setRoleVo(roleVo);
            }

        }
        return pageQuery;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepo.findByUsername(username);

        return user;
    }

    @Override
    public boolean addUser(String username, String password, Long roleId) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setTime(new Date());
        Role role = roleRepo.findById(roleId);
        user.setRole(role);
        User save = userRepo.save(user);
        return save != null;
    }

    @Override
    public boolean delUsers(String userIds) {
        try {
            JSONArray jsonArray = JSONArray.parseArray(userIds);
            for (int i = 0; i < jsonArray.size(); i++) {
                Long id = (Long) jsonArray.get(0);
                userRepo.delete(id);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void updateRoleToUsers(Long roleId, String userIds) {
        Role role = roleRepo.findById(roleId);
        JSONArray jsonArray = JSONArray.parseArray(userIds);
        for (int i = 0; i < jsonArray.size(); i++) {
            Long id = (Long) jsonArray.get(i);
            User user = userRepo.findById(id);
            user.setRole(role);
            userRepo.save(user);
        }
    }

    @Override
    public void updateUser(User user, Long roleId) {
        User userRepoById = userRepo.findById(user.getId());
        Role role = roleRepo.findById(roleId);
        userRepoById.setRole(role);
        userRepoById.setUsername(user.getUsername());
        userRepoById.setPassword(user.getPassword());
        userRepo.save(userRepoById);
    }

    @Override
    public void saveUser(User loginUser) {
        userRepo.save(loginUser);
    }
}
