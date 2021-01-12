package com.wyfx.sf.service;

import com.wyfx.sf.model.SdFile;
import com.wyfx.upms.utils.PageQuery;

import java.util.List;

/**
 * Created by liu on 2017/9/13.
 */
public interface SdFileService {
    List<SdFile> getFileAndFolder(String path, Long deviceId);

    boolean saveUploadFileCommand(Long deviceId, String map);

    void saveDownloadFileCommand(Long deviceId, String path);

    void saveDeleteFileCommand(Long deviceId, String path);

    PageQuery getParameterLog(Long id, Long aLong, Long deviceId, int pageNo, int pageSize) throws Exception;

    void powerOff(Long deviceId);

    void saveIsRemoteCommand(Long deviceId, int status);

    Object getFile(Long deviceId, String path, String filename);

    SdFile findByDeviceIdFirst(Long deviceId);

    void saveFindFileCommand(Long deviceId, String newPath);
}
