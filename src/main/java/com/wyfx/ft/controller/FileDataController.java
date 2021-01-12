package com.wyfx.ft.controller;

import com.wyfx.ft.service.FileDataService;
import com.wyfx.ft.service.FileService;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.DateUtils;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/8/9.
 */
@RestController
@RequestMapping(value = "/fileData")
public class FileDataController {
    @Autowired
    private FileDataService fileDataService;
    @Autowired
    private FileService fileService;

    /**
     * 通过文件ID查询该文件的访问数据
     *
     * @param fileId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFileDataByFileId", method = RequestMethod.GET)
    public Object getFileDataByFileId(Long fileId, int pageNo, int pageSize) throws Exception {
        PageQuery pageQuery = fileDataService.getFileDataByFileId(fileId, pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, pageQuery);
    }

    /**
     * 查询城市访问量排名
     *
     * @param fileId 文件ID
     * @param flag   1,城市 2，省份
     * @return
     */
    @RequestMapping(value = "/getCityOrRegionAndCount", method = RequestMethod.GET)
    public Object getCityOrRegionAndCount(Long fileId, int flag, String startTime, String endTime) {
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";
        //选取多少位排名
        int pageSize = 10;
        List map = fileDataService.getCityOrRegionAndCount(fileId, flag, pageSize, startTime, endTime);

        if (map.size() == 0)
            return new ResponseBody(Constants.EMPTY_ARRAY);

        return new ResponseBody(Constants.SUCCESS_CODE, map);
    }

    /**
     * 获取热点及迁移图
     *
     * @param fileId
     * @param startTime
     * @param endTime
     * @param type      china，中国  2，世界
     * @return
     */
    @RequestMapping(value = "/getCityAndPercent", method = RequestMethod.GET)
    public Object getCityAndPercent(Long fileId, String startTime, String endTime, String type) {
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";

        Map map = fileDataService.getCityAndPercent(fileId, startTime, endTime, type);
        if ((int) map.get("count") == 0)
            return new ResponseBody(Constants.EMPTY_ARRAY);
        return new ResponseBody(Constants.SUCCESS_CODE, map);
    }

    /**
     * 按天统计访问数量
     *
     * @param fileId
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/getCountByDay", method = RequestMethod.GET)
    public Object getCountByDay(Long fileId, String startTime, String endTime) {

        Map map = fileDataService.getCountByDay(fileId, startTime, endTime);
        if ((boolean) map.get("flag"))
            return new ResponseBody(Constants.EMPTY_ARRAY);
        map.remove("flag");
        return new ResponseBody(Constants.SUCCESS_CODE, map);
    }

    /**
     * 获取timelist
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/getDay", method = RequestMethod.GET)
    public Object getDay(String startTime, String endTime) {
        List list = fileDataService.getDay(startTime, endTime);
        return new ResponseBody(Constants.SUCCESS_CODE, list);
    }

    @RequestMapping(value = "/getSameIp", method = RequestMethod.GET)
    public Object getSameIp(Long firstId, Long secondId, String startTime, String endTime, int pageNo, int pageSize) throws Exception {
//        Long firstId = data.getLong("firstId");
//        Long secondId = data.getLong("secondId");
//        String startTime = data.getString("startTime");
//        String endTime = data.getString("endTime");
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";

        PageQuery list = fileDataService.getSameIp(firstId, secondId, DateUtils.stringToDate(startTime), DateUtils.stringToDate(endTime), pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, list);

    }


}
