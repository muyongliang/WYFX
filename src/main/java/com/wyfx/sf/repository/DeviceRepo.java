package com.wyfx.sf.repository;

import com.wyfx.sf.model.Device;

import java.util.List;

/**
 * Created by liu on 2017/9/8.
 */
public interface DeviceRepo extends BaseRepo<Device> {
    List<Device> findByStatus(int status);
}
