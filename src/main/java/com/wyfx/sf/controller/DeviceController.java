package com.wyfx.sf.controller;

import com.wyfx.ft.utils.FileUtils;
import com.wyfx.sf.model.Configuration;
import com.wyfx.sf.model.Device;
import com.wyfx.sf.model.NodeInfo;
import com.wyfx.sf.model.vo.DeviceVo;
import com.wyfx.sf.model.vo.GpsVo;
import com.wyfx.sf.service.DeviceService;
import com.wyfx.sf.utils.DateUtils;
import com.wyfx.sf.utils.ZipUtils;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.ResponseBody;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liu on 2017/9/8.
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    /**
     * 根据状态查询设备
     *
     * @param status 1，在线 0，离线
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllDeviceByStatus", method = RequestMethod.GET)
    public Object getAllDeviceByStatus(Integer status) throws Exception {
        List<DeviceVo> deviceVos = deviceService.getAllDeviceByStatus(status);
        if (deviceVos == null || deviceVos.size() == 0)
            return new ResponseBody(Constants.EMPTY_ARRAY);
        return new ResponseBody<List>(Constants.SUCCESS_CODE, deviceVos);
    }

    /**
     * @param device： id note(备注) name(设备名称) count(磁盘容量)
     * @return
     */
    @RequestMapping(value = "/updateDevice", method = RequestMethod.GET)
    public Object updateDevice(Device device) {
        deviceService.updateDevice(device);
        return new ResponseBody(Constants.SUCCESS_CODE);


    }

    /**
     * 获取硬盘上线离线时间
     *
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/getOnlineAndOfflineTime", method = RequestMethod.GET)
    public Object getOnlineAndOfflineTime(Long deviceId, int pageNo, int pageSize) {
        PageQuery devicestatusVos = deviceService.getOnlineAndOfflineTime(deviceId, pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, devicestatusVos);
    }

    /**
     * 获取文件操作记录
     *
     * @param deviceId  设备ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @RequestMapping(value = "/getOperationLog", method = RequestMethod.GET)
    public Object getOperationLog(Long deviceId, String startTime, String endTime, int pageNo, int pageSize) throws Exception {
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";
        PageQuery diffInfoVos = deviceService.getOperationLog(deviceId, DateUtils.stringToTime(startTime), DateUtils.stringToTime(endTime), pageNo, pageSize);
        //获取网络信息
        NodeInfo nodeInfo = deviceService.getSignalByDeviceId(deviceId);
        Map map = new HashMap();
        if (nodeInfo != null) {
            map.put("signalType", nodeInfo.getNetprovider() + nodeInfo.getNetworktype());
        } else {
            map.put("signalType", "");
        }
        return new ResponseBody(Constants.SUCCESS_CODE, map, diffInfoVos);
    }

    /**
     * 获取位置信息
     *
     * @param deviceId 设备ID
     * @return
     */
    @RequestMapping(value = "/getGps", method = RequestMethod.GET)
    public Object getGps(Long deviceId, int pageNo, int pageSize) throws Exception {
        PageQuery gpses = deviceService.getGps(deviceId, pageNo, pageSize);

        return new ResponseBody(Constants.SUCCESS_CODE, gpses);
    }

    /**
     * 获取位置信息 去重
     *
     * @param deviceId 设备ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getGpsByDistinct", method = RequestMethod.GET)
    public Object getGpsDistinct(Long deviceId) throws Exception {
        List<GpsVo> gpsVos = deviceService.getGpsByDistinct(deviceId);
        if (gpsVos == null || gpsVos.size() == 0)
            return new ResponseBody(Constants.EMPTY_ARRAY);
        return new ResponseBody(Constants.SUCCESS_CODE, gpsVos);
    }

    /**
     * 根据经纬度获取位置信息
     *
     * @param deviceId  设备ID
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getGpsByLongitudeAndLatitude", method = RequestMethod.GET)
    public Object getGpsByLongitudeAndLatitude(Long deviceId, Double longitude, Double latitude, int pageNo, int pageSize) throws Exception {
        PageQuery pageQuery = deviceService.getGpsByLongitudeAndLatitude(deviceId, longitude, latitude, pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, pageQuery);
    }

    /**
     * 获取硬盘配置信息
     *
     * @param deviceId 设备ID
     * @return
     */
    @RequestMapping(value = "/getDeviceConfiguration", method = RequestMethod.GET)
    public Object getDeviceConfiguration(Long deviceId) {
        Configuration configuration = deviceService.getDeviceConfiguration(deviceId);
        Map map = new HashMap();
        Device device = deviceService.getDeviceById(deviceId);
        if (device.getStatus() == -1) {
            map.put("code", Constants.DEVICE_OFF_LINE);
        } else {
            map.put("code", 200);
            //获取网络信息
            NodeInfo nodeInfo = deviceService.getSignalByDeviceId(deviceId);

            if (nodeInfo != null) {
                map.put("signalType", nodeInfo.getNetprovider() + nodeInfo.getNetworktype());
                map.put("signal", nodeInfo.getNetdbm());
            } else {
                map.put("signalType", "");
                map.put("signal", 0);
            }
        }


        if (configuration != null)
            return new ResponseBody(Constants.SUCCESS_CODE, map, configuration);
        return new ResponseBody(Constants.EMPTY_ARRAY);
    }

    /**
     * 更新配置信息
     *
     * @param configuration
     * @return
     */
    @RequestMapping(value = "/updateDeviceConfiguration", method = RequestMethod.GET)
    public Object updateDeviceConfiguration(String configuration) {
        JSONObject jsonObject = JSONObject.fromObject(configuration);
        Configuration config = (Configuration) JSONObject.toBean(jsonObject, Configuration.class);
        Configuration returnConfiguration = deviceService.updateDeviceConfiguration(config);

        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 验证apk更新状态
     *
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/verifyApkUpdateStatus", method = RequestMethod.GET)
    public Object verifyApkUpdateStatus(Long deviceId) {
        Configuration configuration = deviceService.getDeviceConfiguration(deviceId);
        Integer status = configuration.getStatus();
        if (status == 0)
            return new ResponseBody(Constants.FAILED_CODE);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 上传apk更新包
     *
     * @param file    上传文件
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadApk", method = RequestMethod.POST)
    public Object uploadApk(@RequestParam("file") MultipartFile file, HttpServletRequest request, Long deviceId) {
        //apk更新中，就直接返回
        Configuration configuration = deviceService.getDeviceConfiguration(deviceId);
        Integer status = configuration.getStatus();
        if (status == 0)
            return new ResponseBody(Constants.FAILED_CODE);
        //保存上传文件
        String path = FileUtils.saveFile(request, file);

        String apkVersion = "";
        try {
            //解密 文件保存地址
            ZipUtils.decode(path, com.wyfx.sf.utils.Constants.PASSWORD, com.wyfx.sf.utils.Constants.APK_PATH + "/" + deviceId);
            //解密后，将上传，保存的文件删除
            FileUtils.deleteFileAndDirectory(path);
            //读取配置文件
            InputStream in = new FileInputStream(new File(com.wyfx.sf.utils.Constants.APK_PATH + "/" + deviceId + "/" + com.wyfx.sf.utils.Constants.CONFIGURATION_NAME));
            if (in == null) {
                System.err.println("配置文件" + com.wyfx.sf.utils.Constants.CONFIGURATION_NAME + "读取失败");
            } else {
                Properties properties = new Properties();
                properties.load(in);
                apkVersion = properties.getProperty("apkVersion");

            }
        } catch (Exception e) {

            return new ResponseBody(Constants.NO_FILE_CODE);
        }
        return new ResponseBody(Long.valueOf(Constants.SUCCESS_CODE), apkVersion);
    }


}
