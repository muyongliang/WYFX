package com.wyfx.sf.repository;

import com.wyfx.sf.model.ParameterLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by liu on 2017/9/14.
 */
public interface ParameterLogRepo extends BaseRepo<ParameterLog> {

    Page<ParameterLog> findByDeviceIdAndDowncommandIn(Long deviceId, List cmdList, Pageable page);

    Page<ParameterLog> findByDeviceIdAndDowncommandInAndPtimeBetween(Long deviceId, List cmdList, Long startTime, Long endTime, Pageable page);
}
