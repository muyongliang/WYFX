package com.wyfx.cs.controller;

import com.alibaba.fastjson.JSONObject;
import com.wyfx.cs.service.IssuesService;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by liu on 2017/12/13.
 */
@RestController
@RequestMapping(value = "/issues")
public class IssuesController {
    @Autowired
    private IssuesService issuesService;

    @RequestMapping(value = "/aa", method = RequestMethod.GET)
    public Object aa() {
        issuesService.saveIssues("test22");
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 综合分析报表
     *
     * @param data
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/serachIssues", method = RequestMethod.POST)
    public Object serachIssues(@RequestBody JSONObject data) throws Exception {
        Long scanListId = data.getLong("scanListId");
        Map map = issuesService.serachIssues(scanListId);

        return new ResponseBody(Constants.SUCCESS_CODE, map);

    }

    /**
     * 静态扫描报表
     *
     * @param data
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/serachIssuesAndCode", method = RequestMethod.POST)
    public Object serachIssuesAndCode(@RequestBody JSONObject data) throws Exception {
        Long scanListId = data.getLong("scanListId");
        Map map = issuesService.serachIssuesAndCode(scanListId);
        return new ResponseBody(Constants.SUCCESS_CODE, map);
    }

    @RequestMapping(value = "/getCodeByIssuesId", method = RequestMethod.POST)
    public Object getCodeByIssuesId(@RequestBody JSONObject data) throws UnsupportedEncodingException {
        Long issuesId = data.getLong("issuesId");
        Map map = issuesService.getCodeByIssuesId(issuesId);
        return new ResponseBody(Constants.SUCCESS_CODE, map);
    }
}
