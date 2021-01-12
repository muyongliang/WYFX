package com.wyfx.sf.repository;

import com.wyfx.sf.model.BatteryInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by liu on 2017/9/15.
 */
public interface BattereyInfoRepo extends BaseRepo<BatteryInfo> {
    List<BatteryInfo> findByDeviceId(Long deviceId, Pageable page);

    List<BatteryInfo> findByDeviceIdAndPtimeBetween(Long deviceId, Long onlineTime, Long offlineTime);

    List<BatteryInfo> findByDeviceIdAndPtimeAfter(Long deviceId, Long onlineTime);
}
