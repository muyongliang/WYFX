package com.wyfx.cs.controller;

import com.wyfx.cs.service.ScanListService;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.DateUtils;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liu on 2017/12/7.
 * 扫描清单管理
 */
@RestController
@RequestMapping(value = "/scanList")
public class ScanListController {
    @Autowired
    private ScanListService scanListService;

    /**
     * 通过条件获取扫描清单
     *
     * @param status
     * @param pageNo
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getScanListByStatus", method = RequestMethod.GET)
    public Object getScanListByStatus(Integer status, int pageNo, int pageSize, String startTime, String endTime, String keywords) throws Exception {
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";
        PageQuery pageQuery = scanListService.getScanListByStatus(status, pageNo, pageSize, DateUtils.stringToDate(startTime), DateUtils.stringToDate(endTime));
        return new ResponseBody(Constants.SUCCESS_CODE, pageQuery);
    }

//    @RequestMapping(value = "getItem",method = RequestMethod.GET)
//    public Object getItem(int pageNo,int pageSize)

    /**
     * 获取综合分析报表
     *
     * @param scanListId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "searchIssues", method = RequestMethod.GET)
    public Object searchIssues(Long scanListId) throws Exception {
        return new ResponseBody(Constants.SUCCESS_CODE);
    }
}
