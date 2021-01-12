package com.wyfx.upms.shiro;

import com.wyfx.upms.model.Function;
import com.wyfx.upms.model.Role;
import com.wyfx.upms.model.User;
import com.wyfx.upms.repository.FunctionRepo;
import com.wyfx.upms.repository.RoleRepo;
import com.wyfx.upms.repository.UserRepo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: liuxingyu
 * DATE: 2017-06-27.10:55
 * description:自定义Realm,进行认证和授权工作
 * version:
 */
@Component
public class WyfxRealm extends AuthorizingRealm {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FunctionRepo functionRepo;
    @Autowired
    private RoleRepo roleRepo;

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //授权信息对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据当前登录用户查询数据库，获得其对应的权限
        User user = (User) principals.getPrimaryPrincipal();
        if (user.getUsername().equals("admin")) {
            //超级管理员，查询所有权限
            List<Function> list = functionRepo.findAll();
            for (Function function : list) {
                info.addStringPermission(function.getCode());
            }
        } else {
            //普通用户，根据用户查询对应的权限
            Set<User> users = new HashSet<>();
            users.add(user);
            List<Role> roles = roleRepo.findByUsers(user);
            for (Role role : roles) {
                Set<Function> functions = role.getFunctions();
                for (Function function : functions) {
                    info.addStringPermission(function.getCode());
                }
            }
        }
        return info;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken myToken = (UsernamePasswordToken) token;
        String username = myToken.getUsername();
        char[] password = myToken.getPassword();
        // 根据用户名查询数据库中的密码，将密码交给安全管理器，由安全管理器对象负责比较数据库中的密码和页面传递的密码是否一致
        User user = userRepo.findByUsername(username);
        if (user == null) {
            return null;
        }
        // 参数一：签名对象，认证通过后，可以在程序的任意位置获取当前放入的对象
        // 参数二：数据库中查询出的密码
        // 参数三：当前realm的类名
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
                user.getPassword(), this.getClass().getName());
        return info;
    }
}
