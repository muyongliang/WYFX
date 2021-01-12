package com.wyfx.ft.service;

import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.model.FileType;
import com.wyfx.ft.model.Type;
import com.wyfx.ft.model.vo.FileInfoVo;
import com.wyfx.upms.utils.PageQuery;

import java.util.Date;
import java.util.List;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.17:11
 * description:
 * version:
 */
public interface FileService {
    FileInfo save(FileInfo fileInfo);

    List<Type> getFileType() throws Exception;

    PageQuery getAllFileSign(int pageNo, int pageSize) throws Exception;


    PageQuery getAllFileSignByCondition(Long fileTypeId, Date startTime, Date endTime, int pageNo, int pageSize) throws Exception;

    void delFile(Long fileId);

    FileInfo getFileById(Long fileId);

    void signFile(String ids, String tag, String remark, String fileIds, String city, String text) throws Exception;

    List<FileInfoVo> getFileByIds(String fileIds) throws Exception;

    FileType getFileTypeById(Long typeId);

    FileType save(FileType fileType);

    PageQuery getAllFileType(int pageNo, int pageSize) throws Exception;

    FileType getFileTypeByUuid(String uuid);

    void updateVersion(Long id, boolean flag);

    void exportPSO(Long id);

    void updateRemark(Long id, String remark);

    void exportPSOBatch(String ids);

    FileInfoVo getFileNewest() throws Exception;

    PageQuery getAllFileSignOrWatermarkByCondition(Long fileTypeId, Date date, Date date1, int pageNo, int pageSize) throws Exception;

    void saveUuidAndPath(String uuid, FileInfo fileInfo, String city);

}
