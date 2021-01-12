package com.wyfx.sf.repository;

import com.wyfx.sf.model.DeviceInfo;

import java.util.List;

/**
 * Created by liu on 2017/10/24.
 */
public interface DeviceInfoRepo extends BaseRepo<DeviceInfo> {
    List<DeviceInfo> findByDeviceId(Long id);
}
