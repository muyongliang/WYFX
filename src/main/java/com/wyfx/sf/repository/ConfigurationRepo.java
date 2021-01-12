package com.wyfx.sf.repository;

import com.wyfx.sf.model.Configuration;

/**
 * Created by liu on 2017/9/25.
 */
public interface ConfigurationRepo extends BaseRepo<Configuration> {
    Configuration findByDeviceId(Long deviceId);
}
