package com.wyfx.upms.controller;

import com.wyfx.upms.model.User;
import com.wyfx.upms.model.vo.FunctionVo;
import com.wyfx.upms.repository.UserRepo;
import com.wyfx.upms.service.FunctionService;
import com.wyfx.upms.service.UserService;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.ResponseBody;
import com.wyfx.upms.utils.WyfxContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-04.9:45
 * description:权限管理
 * version:
 */
@RestController
@RequestMapping("/function")
public class FunctionController {
    @Autowired
    private FunctionService functionService;
    @Autowired
    private UserService userService;
    //    @RequestMapping(value = "/getMenuList" ,method = RequestMethod.GET)
//    public Object getMenuList(){
//        FunctionVofunctionService.getMenuList();
//    }
    @Autowired
    private UserRepo userRepo;

    /**
     * 获取当前登陆用户的系統菜单
     *
     * @return
     */
    @RequestMapping(value = "/getMenuByLoginUser", method = RequestMethod.GET)
    public Object getMenuByLoginUser() throws Exception {
        String username = WyfxContext.getUsername();
        User loginUser = userService.getUserByUsername(username);
        List<FunctionVo> functionVos = functionService.getMenuByLoginUser(loginUser);
        if (functionVos == null)
            return new ResponseBody(Constants.NO_PERMISSION_CODE);
        return new ResponseBody(Constants.SUCCESS_CODE, functionVos);
    }

    /**
     * 验证页面权限
     *
     * @param path    url路径
     * @param request
     * @return
     */
    @RequestMapping(value = "/verifyFunction", method = RequestMethod.GET)
    public Object verifyFunction(String path, HttpServletRequest request) {
        String username = WyfxContext.getUsername();
        if (username == null) {
            return new ResponseBody(Constants.NOT_LOGINUSER_CODE);
        }
        boolean flag = functionService.verifyFunction(username, path);
        if (flag)
            return new ResponseBody(Constants.SUCCESS_CODE);
        return new ResponseBody(Constants.FAILED_CODE);

    }

    /**
     * 通过系统name获取当前登录用户的菜单
     *
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMenuBySystem", method = RequestMethod.GET)
    public Object getMenuBySystem(String code) throws Exception {


        String username = WyfxContext.getUsername();
        User loginUser = userService.getUserByUsername(username);
        List<FunctionVo> functionVos = functionService.getMenuBySystem(loginUser, code);
        if (functionVos == null)
            return new ResponseBody(Constants.NO_PERMISSION_CODE);
        return new ResponseBody(Constants.SUCCESS_CODE, functionVos);
    }
}
