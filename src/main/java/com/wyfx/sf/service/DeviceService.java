package com.wyfx.sf.service;

import com.wyfx.sf.model.Configuration;
import com.wyfx.sf.model.Device;
import com.wyfx.sf.model.NodeInfo;
import com.wyfx.sf.model.vo.DeviceVo;
import com.wyfx.sf.model.vo.GpsVo;
import com.wyfx.upms.utils.PageQuery;

import java.util.List;

/**
 * Created by liu on 2017/9/8.
 */
public interface DeviceService {
    List<DeviceVo> getAllDeviceByStatus(Integer status) throws Exception;

    void updateDevice(Device device);

    PageQuery getOnlineAndOfflineTime(Long deviceId, int pageNo, int pageSize);

    Device getDeviceById(Long deviceId);

    String getHistoryTimeByDeviceId(Long device);

    String getEnergyByDeviceId(Long deviceId);

    PageQuery getOperationLog(Long deviceId, Long startTime, Long endTime, int pageNo, int pageSize) throws Exception;

    PageQuery getGps(Long deviceId, int pageNo, int pageSize) throws Exception;

    NodeInfo getSignalByDeviceId(Long deviceId);

    int getIsRemoteStatus(Long deviceId);


    Configuration getDeviceConfiguration(Long deviceId);

    Configuration updateDeviceConfiguration(Configuration configuration);

    List<GpsVo> getGpsByDistinct(Long deviceId) throws Exception;

    PageQuery getGpsByLongitudeAndLatitude(Long deviceId, Double longitude, Double latitude, int pageNo, int pageSize) throws Exception;


    int getBatteryInfoByDeviceId(Long deviceId);
}
