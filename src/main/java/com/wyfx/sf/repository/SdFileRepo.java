package com.wyfx.sf.repository;

import com.wyfx.sf.model.SdFile;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by liu on 2017/9/13.
 */
public interface SdFileRepo extends BaseRepo<SdFile> {
    List<SdFile> findByDeviceId(Long deviceId, Pageable page);

    List<SdFile> findByDeviceIdAndPath(Long deviceId, String path);

    SdFile findByDeviceIdAndPathAndFilename(Long deviceId, String path, String filename);
}
