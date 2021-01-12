package com.wyfx.upms.repository;

import com.wyfx.upms.model.Function;
import com.wyfx.upms.model.Role;

import java.util.List;
import java.util.Set;

/**
 * Author: liuxingyu
 * DATE: 2017-06-30.11:01
 * description:
 * version:
 */
public interface FunctionRepo extends BaseRepo<Function> {

    List<Function> findByRolesAndGeneratemenu(Set<Role> roles, int yesMenu);

    List<Function> findByRoles(Set<Role> roles);

    List<Function> findByRolesAndRank(Set<Role> roles, int oneMenu);


    List<Function> findByRolesAndCode(Set<Role> roles, String code);

    Function findByCode(String code);

    List<Function> findByRolesAndParentFunction(Set<Role> roles, Function function);
}
