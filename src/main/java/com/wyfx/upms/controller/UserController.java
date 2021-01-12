package com.wyfx.upms.controller;

import com.wyfx.upms.model.User;
import com.wyfx.upms.repository.UserRepo;
import com.wyfx.upms.service.UserService;
import com.wyfx.upms.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: liuxingyu
 * DATE: 2017-06-27.10:57
 * description:用户管理
 * version:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 获取当前登陆用户
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Autowired
    private UserRepo userRepo;

    /**
     * 生成验证码
     *
     * @param request
     * @param response
     * @param time
     * @return
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public Object code(HttpServletRequest request, HttpServletResponse response, String time) {

        response.setContentType("image/jpeg");//设置响应类型，告知浏览器输出的是图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandomCode(request, response);//生成图片并通过response输出
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 用户登陆
     *
     * @param code 验证码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(String username, String password, String code) {
        //session中获取验证码
        String sCode = WyfxContext.getSession().getAttribute("codeValidate").toString();
        if (code != null && sCode.equalsIgnoreCase(code)) {
            Subject subject = SecurityUtils.getSubject();
            //String username = user.getUsername();
            //String password = user.getPassword();
            //使用md5对密码进行加密
            // password = MD5Utils.md5(password);
            AuthenticationToken token = new UsernamePasswordToken(username, password);

            //调用安全管理器，安全管理器调用Realm
            try {
                subject.login(token);
                User loginUser = (User) subject.getPrincipal();
                // 登录成功，将user放入session
                WyfxContext.saveUsername(username);

            } catch (UnknownAccountException e) {
                System.err.println("用户名不存在");
                return new ResponseBody(Long.valueOf(Constants.NO_USER_CODE), BundleUtils.getValue(Constants.unameErrMes));
            } catch (IncorrectCredentialsException e) {
                System.err.println("密码错误");
                return new ResponseBody(Long.valueOf(Constants.ERROR_PASSWORD_CODE), BundleUtils.getValue(Constants.pwdErrMes));
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("验证码错误");
            return new ResponseBody(Long.valueOf(Constants.ERROR_CODE), BundleUtils.getValue(Constants.codeErrMes));
        }
        return new ResponseBody(Constants.SUCCESS_CODE);

    }

    /**
     * 退出登陆
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout() {
        WyfxContext.setUsername();
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 修改密码
     *
     * @param
     * @param oldPass 旧密码
     * @param newPass 新密码
     * @return
     */
    @RequestMapping(value = "/updatePass", method = RequestMethod.GET)
    public Object updatePass(String oldPass, String newPass) {
        String username = WyfxContext.getUsername();
        User loginUser = userService.getUserByUsername(username);
        //验证旧密码是否正确
        User user = userService.getUserByIdAndPassword(loginUser.getId(), oldPass);
        if (user == null)
            return new ResponseBody(Long.valueOf(Constants.ERROR_PASSWORD_CODE), BundleUtils.getValue(Constants.erroldPass));
        else {
            //修改密码
            User saveUser = userService.saveNewPass(loginUser.getId(), newPass);
            if (saveUser != null) {
                username = saveUser.getUsername();
                //更新session中用户信息
                WyfxContext.saveUsername(username);
                return new ResponseBody(Long.valueOf(Constants.SUCCESS_CODE), BundleUtils.getValue(Constants.successUpass));
            }
        }
        return null;
    }

    @RequestMapping(value = "/getLoginUser", method = RequestMethod.GET)
    public Object getLoginUser(HttpServletRequest request) throws Exception {
        //  User user = userRepo.findById(1l);
        // WyfxContext.saveUsername(user.getUsername());
        String username = WyfxContext.getUsername();
        if (username == null)
            return new ResponseBody(Constants.NOT_LOGINUSER_CODE);
        return new ResponseBody(Long.valueOf(Constants.SUCCESS_CODE), username);
    }

    /**
     * 查询所有用户 分页
     *
     * @return
     */
    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    public Object getAllUser(int pageNo, int pageSize) throws Exception {
        PageQuery pageQuery = userService.getAllUser(pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, pageQuery);
    }

    /**
     * 添加用户
     *
     * @param username 用户名
     * @param password 密码
     * @param roleId   角色id
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public Object addUser(String username, String password, Long roleId) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return new ResponseBody(Constants.USER_EXIST_CODE);
        }
        boolean flag = userService.addUser(username, password, roleId);
        if (flag)
            return new ResponseBody(Constants.SUCCESS_CODE);
        return new ResponseBody(Constants.FAILED_CODE);
    }

    /**
     * 删除用户 多个
     *
     * @param userIds 多个用户id ，数组格式
     * @return
     */
    @RequestMapping(value = "/delUsers", method = RequestMethod.GET)
    public Object delUsers(String userIds) {
        boolean flag = userService.delUsers(userIds);
        if (flag)
            return new ResponseBody(Constants.SUCCESS_CODE);
        return new ResponseBody(Constants.FAILED_CODE);
    }

    /**
     * 用户批量更改角色
     *
     * @param roleId  角色id
     * @param userIds 多个用户id 数组格式
     * @return
     */
    @RequestMapping(value = "/updateRoleToUsers", method = RequestMethod.GET)
    public Object updateRoleToUsers(Long roleId, String userIds) {
        userService.updateRoleToUsers(roleId, userIds);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 修改用户
     *
     * @param user
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public Object updateUser(User user, Long roleId) {
        userService.updateUser(user, roleId);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }
}

