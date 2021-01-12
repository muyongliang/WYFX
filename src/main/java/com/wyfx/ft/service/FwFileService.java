package com.wyfx.ft.service;

import com.wyfx.ft.model.FwFileInfo;
import com.wyfx.ft.model.FwType;
import com.wyfx.upms.utils.PageQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/8/18.
 */
public interface FwFileService {
    PageQuery getAllFwFile(int pageNo, int pageSize, int flag, String filename, Long type, String s, String ip, String startTime, String endTime) throws Exception;

    FwFileInfo getFileByFwFileId(Long fileId);

    List<FwType> getFileType();

    void updateRemark(Long id, String remark);

    Map getFwFileCountAndPercent();

    boolean isExistFile(Long fwFileId);
}
