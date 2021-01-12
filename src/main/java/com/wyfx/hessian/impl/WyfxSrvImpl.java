package com.wyfx.hessian.impl;

import com.wyfx.hessian.service.WyfxService;
import com.wyfx.upms.repository.FunctionRepo;
import com.wyfx.upms.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: liuxingyu
 * DATE: 2017-07-19.15:18
 * description:
 * version:
 */
@Service("WyfxService")
public class WyfxSrvImpl implements WyfxService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FunctionRepo functionRepo;

}
