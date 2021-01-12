package com.wyfx.ft.service;

import com.wyfx.upms.utils.PageQuery;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/8/9.
 */
public interface FileDataService {
    PageQuery getFileDataByFileId(Long fileId, int pageNo, int pageSize) throws Exception;


    List getCityOrRegionAndCount(Long fileId, int flag, int pageSize, String date, String stringToDate);

    Map getCityAndPercent(Long fileId, String startTime, String endTime, String type);

    Map getCountByDay(Long fileId, String startTime, String endTime);

    List getDay(String startTime, String endTime);

    PageQuery getSameIp(Long firstId, Long secondId, Date startTime, Date endTime, int pageNo, int pageSize) throws Exception;
}
