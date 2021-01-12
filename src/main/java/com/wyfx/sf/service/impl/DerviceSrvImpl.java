package com.wyfx.sf.service.impl;

import com.wyfx.sf.model.*;
import com.wyfx.sf.model.vo.DeviceVo;
import com.wyfx.sf.model.vo.DevicestatusVo;
import com.wyfx.sf.model.vo.DiffInfoVo;
import com.wyfx.sf.model.vo.GpsVo;
import com.wyfx.sf.repository.*;
import com.wyfx.sf.service.DeviceService;
import com.wyfx.sf.utils.Constants;
import com.wyfx.sf.utils.DateUtils;
import com.wyfx.upms.utils.ModelChange;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liu on 2017/9/8.
 */
@Service
@Transactional
public class DerviceSrvImpl implements DeviceService {
    @Autowired
    private DeviceRepo deviceRepo;
    @Autowired
    private DevicestatusRepo devicestatusRepo;
    @Autowired
    private BattereyInfoRepo battereyInfoRepo;
    @Autowired
    private DiffInfoRepo diffInfoRepo;
    @Autowired
    private GpsRepo gpsRepo;
    @Autowired
    private NodeInfoRepo nodeInfoRepo;
    @Autowired
    private CommandRepo commandRepo;
    @Autowired
    private ConfigurationRepo configurationRepo;
    @Autowired
    private DeviceInfoRepo deviceInfoRepo;

    @Override
    public List<DeviceVo> getAllDeviceByStatus(Integer status) throws Exception {
        List<Device> devices;
        if (status != null && status != 0 && status != -1) {
            //查询全部
            devices = deviceRepo.findAll();
        } else {
            //根据状态查询设备
            devices = deviceRepo.findByStatus(status);
        }

        //获取硬盘容量
        for (Device device : devices) {
            String count = device.getCount();
            if (count == null || "".equals(count) || "0".equals(count)) {
                List<DeviceInfo> deviceInfos = deviceInfoRepo.findByDeviceId(device.getId());
                for (DeviceInfo deviceInfo : deviceInfos) {
                    if ("SSDSTORAGE".equals(deviceInfo.getDesciption())) {
                        device.setCount(deviceInfo.getValue());
                        deviceRepo.save(device);
                        break;
                    }
                }
            }

        }

        return ModelChange.changeList(DeviceVo.class, devices);

    }

    @Override
    public void updateDevice(Device device) {
        Device deviceRepoOne = deviceRepo.findOne(device.getId());
        String name = device.getName();
        if (name != null && !"".equals(name))
            deviceRepoOne.setName(name);

        String count = device.getCount();
        if (count != null && !"".equals(count))
            deviceRepoOne.setCount(count);

        String note = device.getNote();
        if (note != null && !"".equals(note))
            deviceRepoOne.setNote(note);

        //save
        deviceRepo.save(deviceRepoOne);

    }

    @Override
    public PageQuery getOnlineAndOfflineTime(Long deviceId, int pageNo, int pageSize) {
        int size = devicestatusRepo.findByDeviceId(deviceId).size();

        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setTotalElements(size / 2);

        Sort sort = new Sort(Sort.Direction.DESC, "Id");
        Pageable page = new PageRequest(pageNo - 1, 2 * pageSize, sort);
        List<Devicestatus> devicestatuses = devicestatusRepo.findByDeviceId(deviceId, page);
        int status;
        if (devicestatuses != null && devicestatuses.size() > 0) {

            status = devicestatuses.get(0).getStatus();

        } else {
            return pageQuery;
        }
        //封装参数
        List<DevicestatusVo> devicestatusVos = new ArrayList<>();
        for (int i = 0; i < devicestatuses.size(); i += 2) {
            DevicestatusVo devicestatusVo = new DevicestatusVo();

            if (i + 1 >= devicestatuses.size())
                break;

            if (status == 0 && i == 0) {
                devicestatusVo.setOnlineTime(DateUtils.stampToDate(devicestatuses.get(0).getTime().toString()));
                // devicestatusVo.setOfflineTime("");
                i--;
            } else {
                devicestatusVo.setOnlineTime(DateUtils.stampToDate(devicestatuses.get(i + 1).getTime().toString()));
                devicestatusVo.setOfflineTime(DateUtils.stampToDate(devicestatuses.get(i).getTime().toString()));
            }

            Long onlineTime = DateUtils.stringToTime(devicestatusVo.getOnlineTime());
            Long offlineTime = DateUtils.stringToTime(devicestatusVo.getOfflineTime());
            List<BatteryInfo> batteryInfos;
            if (offlineTime != null) {
                batteryInfos = battereyInfoRepo.findByDeviceIdAndPtimeBetween(deviceId, onlineTime, offlineTime);
            } else {
                batteryInfos = battereyInfoRepo.findByDeviceIdAndPtimeAfter(deviceId, onlineTime);
            }


            for (BatteryInfo batteryInfo : batteryInfos) {
                if ("没有充电".equals(batteryInfo.getStatus())) {

                    devicestatusVo.setControllTime(DateUtils.stampToDate(batteryInfo.getPtime().toString()));
                    break;
                }
            }


            devicestatusVos.add(devicestatusVo);
        }

        pageQuery.setContent(devicestatusVos);

        return pageQuery;
    }

    @Override
    public Device getDeviceById(Long deviceId) {
        return deviceRepo.findOne(deviceId);
    }

    @Override
    public String getHistoryTimeByDeviceId(Long deviceId) {
        List<Devicestatus> devicestatuses = devicestatusRepo.findByDeviceId(deviceId);
        if (devicestatuses != null && devicestatuses.size() > 0) {
            Devicestatus devicestatus = devicestatuses.get(devicestatuses.size() - 1);
            return DateUtils.stampToDate(devicestatus.getTime().toString());
        }
        return null;
    }

    @Override
    public String getEnergyByDeviceId(Long deviceId) {
        Sort sort = new Sort(Sort.Direction.DESC, "Id");
        Pageable page = new PageRequest(0, 1, sort);
        List<BatteryInfo> batteryInfos = battereyInfoRepo.findByDeviceId(deviceId, page);
        if (batteryInfos != null && batteryInfos.size() > 0) {
            return String.valueOf(batteryInfos.get(0).getLevel());
        }
        return null;
    }

    @Override
    public PageQuery getOperationLog(Long deviceId, Long startTime, Long endTime, int pageNo, int pageSize) throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "Ptime");
        Pageable page = new PageRequest(pageNo - 1, pageSize, sort);
        Page<DiffInfo> diffInfos;
        if (startTime != null && endTime != null) {
            diffInfos = diffInfoRepo.findByDeviceIdAndPtimeBetween(deviceId, startTime, endTime, page);
        } else {
            diffInfos = diffInfoRepo.findByDeviceId(deviceId, page);
        }
        PageQuery pageQuery = PageUtils.pagingList(DiffInfo.class, diffInfos, pageNo, pageSize);
        List<DiffInfo> content = pageQuery.getContent();
        List<DiffInfoVo> diffInfoVos = new ArrayList<>();
        for (DiffInfo diffInfo : content) {
            DiffInfoVo diffInfoVo = new DiffInfoVo();
            diffInfoVo.setId(diffInfo.getId());
            diffInfoVo.setDeviceId(deviceId);
            diffInfoVo.setPath(diffInfo.getPath());
            diffInfoVo.setTime(DateUtils.stampToDate(diffInfo.getPtime().toString()));
            diffInfoVo.setType(diffInfo.getType());
            diffInfoVos.add(diffInfoVo);
        }
        pageQuery.setContent(diffInfoVos);
        return pageQuery;
    }

    @Override
    public PageQuery getGps(Long deviceId, int pageNo, int pageSize) throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "Id");
        Pageable page = new PageRequest(pageNo - 1, pageSize, sort);
        Page<Gps> gpses = gpsRepo.findByDeviceId(deviceId, page);
        PageQuery pageQuery = PageUtils.pagingList(GpsVo.class, gpses, pageNo, pageSize);
        return pageQuery;
    }

    @Override
    public NodeInfo getSignalByDeviceId(Long deviceId) {
        Sort sort = new Sort(Sort.Direction.DESC, "Id");
        Pageable page = new PageRequest(0, 1, sort);
        List<NodeInfo> nodeInfos = nodeInfoRepo.findByDeviceId(deviceId, page);
        if (nodeInfos != null && nodeInfos.size() > 0)
            return nodeInfos.get(0);
        return null;
    }

    @Override
    public int getIsRemoteStatus(Long deviceId) {
        Configuration configuration = configurationRepo.findByDeviceId(deviceId);
        if (configuration != null)
            return configuration.getRemoteStatus();
        return 1;
    }

    @Override
    public Configuration getDeviceConfiguration(Long deviceId) {
        Configuration configuration = configurationRepo.findByDeviceId(deviceId);

        if (configuration == null) {
            configuration = new Configuration();
            configuration.setDeviceId(deviceId);
            Configuration save = configurationRepo.save(configuration);
            String url1 = configuration.getUrl1();
            if (url1 == null || "".equals(url1))
                configuration.setUrl1(Constants.SERVER_IP);
            return save;
        } else {
            String url1 = configuration.getUrl1();
            if (url1 == null || "".equals(url1))
                configuration.setUrl1(Constants.SERVER_IP);
            String stayApkVersion = configuration.getStayApkVersion();
            if (stayApkVersion == null || "".equals(stayApkVersion))
                return configuration;
            Command command = commandRepo.findByDeviceIdAndCmd(deviceId, Constants.UPDATE_APK_CMD);
            if (command == null) {
                //apk更新完成
                configuration.setStatus(Constants.UPDATED_STATUS);
                configuration.setApkVersion(configuration.getStayApkVersion());
                configuration.setStayApkVersion(null);
            } else {
                //检查更新包文件是否存在
                String args = command.getArgs();
                String[] strings = args.split(":");
                File uploadFile = new File("c:/upload/" + strings[0]);
                if (!uploadFile.exists()) {
                    //如果文件不存在，表示正在更新
                    configuration.setStatus(Constants.UPDATING_STATUS);
                }
            }

        }

        configurationRepo.save(configuration);
        return configuration;
    }

    @Override
    public Configuration updateDeviceConfiguration(Configuration configuration) {

        configuration = configurationRepo.save(configuration);
        //保存命令
        //URL
        saveUrl(configuration);

        //保存基站
        saveBaseStation(configuration);
        //保存WiFi
        saveWifi(configuration);
        //保存上线等待时间
        saveOnlineWaitTime(configuration);
        //保存休眠信息
        saveDormant(configuration);
        //保存apk更新信息
        saveUpdateApk(configuration);
        //保存扫描目录配置
        saveScanPath(configuration);

        return configuration;
    }

    private void saveScanPath(Configuration configuration) {
        Integer status = configuration.getScanStatus();
        String path = configuration.getPath();
        Command command = getCommand(configuration.getDeviceId(), Constants.SACN_PATH_CMD);
        if (status == 1 && path != null && !"".equals(path)) {
            String[] strings = path.split(",");
            String s = StringUtils.join(strings, "#");
            command.setArgs(s);
        } else {
            command.setArgs("");
        }
        commandRepo.save(command);

    }

    /**
     * 保存apk更新信息
     *
     * @param configuration
     */
    private void saveUpdateApk(Configuration configuration) {
        String stayApkVersion = configuration.getStayApkVersion();
        Long deviceId = configuration.getDeviceId();
        //拼接参数 6x1505269797817:/storage/sdcard0/ssd/ssd_ntfs_1/散落的花瓣视频.mp4
        if (stayApkVersion != null && !"".equals(stayApkVersion)) {
            Long time = new Date().getTime();
            String filename = deviceId + "x" + time;
            File file = new File(Constants.APK_PATH + File.separator + deviceId + File.separator + Constants.APK_NAME);
            if (file.exists()) {
                file.renameTo(new File("C:/upload/" + filename));
                //删除apk文件
                file.delete();
                //删除配置文件
                new File(Constants.APK_PATH + File.separator + deviceId + File.separator + Constants.CONFIGURATION_NAME).delete();
            }

            String args = filename + ":" + "/storage/sdcard0/UpdatePush.apk";
            Command command = getCommand(deviceId, Constants.UPDATE_APK_CMD);
            command.setArgs(args);
            commandRepo.save(command);
        }
    }

    @Override
    public List<GpsVo> getGpsByDistinct(Long deviceId) throws Exception {
        List<Gps> gpses = gpsRepo.getGpsByDistinct(deviceId);
        List<GpsVo> gpsVos = ModelChange.changeList(GpsVo.class, gpses);
        return gpsVos;
    }

    @Override
    public PageQuery getGpsByLongitudeAndLatitude(Long deviceId, Double longitude, Double latitude, int pageNo, int pageSize) throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "Id");
        Pageable page = new PageRequest(pageNo - 1, pageSize, sort);
        Page<Gps> gpses = gpsRepo.findByDeviceIdAndLongitudeAndLatitude(deviceId, longitude, latitude, page);
        PageQuery pageQuery = PageUtils.pagingList(GpsVo.class, gpses, pageNo, pageSize);
        return pageQuery;
    }


    @Override
    public int getBatteryInfoByDeviceId(Long deviceId) {
        Sort sort = new Sort(Sort.Direction.DESC, "Id");
        Pageable page = new PageRequest(0, 1, sort);
        List<BatteryInfo> batteryInfos = battereyInfoRepo.findByDeviceId(deviceId, page);
        if (batteryInfos != null && batteryInfos.size() > 0) {
            BatteryInfo batteryInfo = batteryInfos.get(0);
            if ("没有充电".equals(batteryInfo.getStatus()))
                return 0;
        }
        return -1;
    }

    /**
     * 保存休眠信息
     *
     * @param configuration
     */
    private void saveDormant(Configuration configuration) {
        Command command = getCommand(configuration.getDeviceId(), Constants.DORMANT_CMD);
        // Integer dormant = configuration.getDormant();
        int dormantTime = configuration.getDormantTime();
        if (dormantTime != 0) {
            command.setArgs(String.valueOf(dormantTime));
            commandRepo.save(command);
        }
    }

    /**
     * 保存上线等待时间
     *
     * @param configuration
     */
    private void saveOnlineWaitTime(Configuration configuration) {
        Command command = getCommand(configuration.getDeviceId(), Constants.ONLINE_WATI_CMD);
        int onlineWaitTime = configuration.getOnlineWaitTime();
        if (onlineWaitTime != 0 && configuration.getRemoteStatus() != 1) {

            command.setArgs(String.valueOf(onlineWaitTime));
            commandRepo.save(command);
        }
    }

    /**
     * 保存WiFi
     *
     * @param configuration
     */
    private void saveWifi(Configuration configuration) {
        Integer wifi = configuration.getWifi();
        if (wifi == 0)
            return;
        //// TODO: 2017/10/9
        Command command = getCommand(configuration.getDeviceId(), Constants.WIFI_CMD);
        String wifiName = configuration.getWifiName();
        String wifiPassword = configuration.getWifiPassword();
        if ("".equals(wifiName) || "".equals(wifiPassword)) {

            command.setArgs(wifi.toString());
        } else {
            command.setArgs(wifi + "#" + wifiName + "#" + wifiPassword);
        }


    }

    /**
     * 保存基站
     *
     * @param configuration
     */
    private void saveBaseStation(Configuration configuration) {
        Integer baseStation = configuration.getBaseStation();
        if (baseStation == 0) return;
        //// TODO: 2017/10/9  
        Command command = getCommand(configuration.getDeviceId(), Constants.BASE_STATION_CMD);
        command.setArgs(configuration.getBaseStation().toString());
        commandRepo.save(command);
    }


    /**
     * 保存URL
     *
     * @param configuration
     * @return
     */
    private void saveUrl(Configuration configuration) {
        String url1 = configuration.getUrl1();
        String url2 = configuration.getUrl2();
        String url3 = configuration.getUrl3();

        Long deviceId = configuration.getDeviceId();

        String url = "";
        if (url1 != null && !"".equals(url1))
            url = url + url1 + "#";
        if (url2 != null && !"".equals(url2))
            url = url + url2 + "#";
        if (url3 != null && !"".equals(url3))
            url = url + url3 + "#";

        if (!"".equals(url)) {

            Command command = getCommand(deviceId, Constants.URL_CMD);
            command.setArgs(url.substring(0, url.length() - 1));
            commandRepo.save(command);

        }

    }

    /**
     * 获取command对象
     *
     * @param deviceId
     * @param cmd
     * @return
     */
    private Command getCommand(Long deviceId, int cmd) {
        Command command = commandRepo.findByDeviceIdAndCmd(deviceId, cmd);
        if (command == null) {
            command = new Command();
            command.setDeviceId(deviceId);
            command.setCmd(cmd);
        }
        return command;
    }


}
