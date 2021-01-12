package com.wyfx.upms.repository;

import com.wyfx.upms.model.Role;
import com.wyfx.upms.model.User;

import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-06-30.11:07
 * description:
 * version:
 */
public interface RoleRepo extends BaseRepo<Role> {
    List<Role> findByUsers(User user);

    Role findById(Long roleId);
}
