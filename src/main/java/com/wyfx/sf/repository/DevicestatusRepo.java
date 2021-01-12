package com.wyfx.sf.repository;

import com.wyfx.sf.model.Devicestatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by liu on 2017/9/14.
 */
public interface DevicestatusRepo extends BaseRepo<Devicestatus> {
    List<Devicestatus> findByDeviceId(Long deviceId);


    List<Devicestatus> findByDeviceId(Long deviceId, Pageable page);
}
