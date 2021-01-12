package com.wyfx.cs.service;

import com.wyfx.upms.utils.PageQuery;

import java.util.Date;

/**
 * Created by liu on 2017/12/7.
 */
public interface ScanListService {
    PageQuery getScanListByStatus(Integer status, int pageNo, int pageSize, Date date, Date stringToDate) throws Exception;

}
