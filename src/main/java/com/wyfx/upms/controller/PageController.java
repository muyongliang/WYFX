package com.wyfx.upms.controller;

import com.wyfx.ft.model.FileType;
import com.wyfx.ft.repository.FileTypeRepo;
import com.wyfx.upms.repository.UserRepo;
import com.wyfx.upms.service.UserService;
import com.wyfx.upms.utils.BundleUtils;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.ResponseBody;
import com.wyfx.upms.utils.WyfxContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author: liuxingyu
 * DATE: 2017-06-27.11:55
 * description:国际化页面切换
 * version:
 */
@RestController
@RequestMapping("/page")
public class PageController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FileTypeRepo fileTypeRepo;

    /**
     * 获取页面参数
     *
     * @param msg 页面识别
     * @return
     */
    @RequestMapping(value = "/getPageMsg", method = RequestMethod.GET)
    public Object getPageMsg(String msg) {
        //判断msg，若msg为FileMake 在uploadTip后面加上 word，excel)
        Map map = BundleUtils.getMap(msg);
        if ("FileMake".equals(msg)) {
            String uploadTip = map.get("uploadTip").toString();
            List<FileType> fileTypes = fileTypeRepo.findByStatus(Constants.YES_FILE_TYPE);
            for (FileType fileType : fileTypes) {
                uploadTip += fileType.getSwf() + ",";
            }
            uploadTip = uploadTip.substring(0, uploadTip.length() - 1);
            map.put("uploadTip", uploadTip);
        }
        return new ResponseBody(Constants.SUCCESS_CODE, map);
    }

    /**
     * 切换语言
     *
     * @param msg 语言类型
     * @return
     */
    @RequestMapping(value = "/changeLanguage", method = RequestMethod.GET)
    public Object changeLanguage(String msg) {
        WyfxContext.saveLangue(msg);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 系统挑转
     *
     * @param request
     * @param path
     * @throws IOException
     */
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public Object redirect(HttpServletRequest request, String path) throws IOException {
        String ip = Constants.SERVER_IP;//返回服务器的IP地址
        String url = "/wyfx/" + path + "/index.html";

        if (path.equals("IOS")) {
            url = "http://182.61.56.142:8080";
        } else if (path.equals("Android")) {
            url = "http://47.92.0.107/MMCP/android/index.html";
        } else if (path.equals("Vikit")) {
            url = "http://47.92.118.135:8081/";
        }
        return new ResponseBody(Long.valueOf(Constants.SUCCESS_CODE), url);
    }

    /**
     * 获取当前语言类型
     *
     * @return
     */
    @RequestMapping(value = "/getLangueType", method = RequestMethod.GET)
    public Object getLangueType() {
        String langue = WyfxContext.getLangue();
        if (langue == null) {
            langue = Constants.DEFAULT_LANGUE;
            WyfxContext.saveLangue(langue);
        }
        return new ResponseBody(Long.valueOf(Constants.SUCCESS_CODE), langue);
    }

}
