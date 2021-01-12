package com.wyfx.sf.repository;

import com.wyfx.sf.model.DiffInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by liu on 2017/9/18.
 */
public interface DiffInfoRepo extends BaseRepo<DiffInfo> {
    Page<DiffInfo> findByDeviceIdAndPtimeBetween(Long deviceId, Long startTime, Long endTime, Pageable page);

    Page<DiffInfo> findByDeviceId(Long deviceId, Pageable page);
}
