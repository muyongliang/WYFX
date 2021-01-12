package com.wyfx.upms.service;

import com.wyfx.upms.model.User;
import com.wyfx.upms.model.vo.FunctionVo;

import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-04.9:50
 * description:
 * version:
 */
public interface FunctionService {
    List<FunctionVo> getMenuByLoginUser(User loginUser) throws Exception;


    boolean verifyFunction(String loginUser, String path);

    List<FunctionVo> getMenuBySystem(User loginUser, String code) throws Exception;
}
