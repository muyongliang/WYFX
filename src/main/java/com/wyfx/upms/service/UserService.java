package com.wyfx.upms.service;

import com.wyfx.upms.model.User;
import com.wyfx.upms.utils.PageQuery;

/**
 * Author: liuxingyu
 * DATE: 2017-06-30.10:14
 * description:
 * version:
 */
public interface UserService {
    User getUserByIdAndPassword(Long id, String password);

    User saveNewPass(Long id, String newPass);

    PageQuery getAllUser(int pageNo, int pageSize) throws Exception;

    User getUserByUsername(String username);

    boolean addUser(String username, String password, Long roleId);

    boolean delUsers(String userIds);

    void updateRoleToUsers(Long roleId, String userIds);

    void updateUser(User user, Long roleId);

    void saveUser(User loginUser);
}
