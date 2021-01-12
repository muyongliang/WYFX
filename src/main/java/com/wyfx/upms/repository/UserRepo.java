package com.wyfx.upms.repository;

import com.wyfx.upms.model.User;

/**
 * Author: liuxingyu
 * DATE: 2017-07-10.14:38
 * description:
 * version:
 */
public interface UserRepo extends BaseRepo<User> {
    User findById(Long id);

    User findByUsername(String username);

    User findByIdAndPassword(Long id, String password);

}
