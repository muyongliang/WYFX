package com.wyfx.sf.controller;

import com.wyfx.sf.model.Device;
import com.wyfx.sf.model.NodeInfo;
import com.wyfx.sf.model.SdFile;
import com.wyfx.sf.repository.SdFileRepo;
import com.wyfx.sf.service.DeviceService;
import com.wyfx.sf.service.SdFileService;
import com.wyfx.sf.utils.DateUtils;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.ResponseBody;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/9/12.
 * 磁盘文件管理
 */
@RestController
@RequestMapping("/sdFile")
public class SdFileController {
    @Autowired
    private SdFileService sdFileService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SdFileRepo sdFileRepo;

    /**
     * 通过路径查询文件目录
     *
     * @param path     路径
     * @param deviceId 设备Id
     * @return
     */
    @RequestMapping(value = "/getFileAndFolder", method = RequestMethod.GET)
    public Object getFileAndFolder(String path, Long deviceId) {

        Device device = deviceService.getDeviceById(deviceId);
        //path为空，代表查询根录
        if (path == null || "".equals(path)) {
            SdFile sdFile = sdFileService.findByDeviceIdFirst(deviceId);
            // List<SdFile> sdFiles = sdFileRepo.findByDeviceId(deviceId);
            if (sdFile != null)
                path = sdFile.getPath();

        } else {
            path = updatePath(path, deviceId);

        }
        List<SdFile> sdFiles = sdFileService.getFileAndFolder(path, deviceId);

        Map map = new HashMap();
        map.put("status", device.getStatus());
        int deviceStatus = -1;
        if (device.getStatus() == 0) {
            deviceStatus = deviceService.getBatteryInfoByDeviceId(deviceId);
        }
        map.put("status", deviceStatus);
        if (device.getStatus() == -1) {
            String time = deviceService.getHistoryTimeByDeviceId(deviceId);
            map.put("time", time);
        } else {
            String energy = deviceService.getEnergyByDeviceId(deviceId);
            map.put("energy", energy + "%");
            int signal = deviceService.getSignalByDeviceId(deviceId).getNetdbm();
            map.put("signal", signal);
            int status = deviceService.getIsRemoteStatus(deviceId);
            map.put("remoteStatus", status);

        }
        if (path.indexOf("/storage/sdcard0") != -1) {
            path = path.substring(16);
        } else {
            path = path.substring(8);
        }
        map.put("path", path);
        if (sdFiles == null || sdFiles.size() == 0)
            return new ResponseBody(Constants.EMPTY_ARRAY, map, null);

        for (SdFile sdFile : sdFiles) {
            if (sdFile.getPath().indexOf("/storage/sdcard0") != -1) {
                sdFile.setPath(sdFile.getPath().substring(16));
            } else {
                sdFile.setPath(sdFile.getPath().substring(8));
            }

        }

        return new ResponseBody(Constants.SUCCESS_CODE, map, sdFiles);

    }

    /**
     * 上传操作
     *
     * @param deviceId 设备id
     * @param file     文件对象
     * @param path     文件上传到的位置
     * @return
     */
    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public Object uploadFile(Long deviceId, @RequestParam("file") MultipartFile file, String path) throws IOException {
        path = updatePath(path, deviceId);
        String name = upload(deviceId, file);
        String filename = file.getOriginalFilename();
        Map map = new HashMap();
        map.put("path", path + "/" + filename);
        map.put("name", name);
        //拼接参数 6x1505269797817:/storage/sdcard0/ssd/ssd_ntfs_1/散落的花瓣视频.mp4
        String args = name + ":" + path + "/" + filename;
        boolean flag = sdFileService.saveUploadFileCommand(deviceId, args);
        if (flag) return new ResponseBody(Constants.SUCCESS_CODE);
        return new ResponseBody(Constants.FAILED_CODE);

    }

    /**
     * 文件上传
     *
     * @param deviceId
     * @param file
     * @return
     * @throws IOException
     */
    public String upload(Long deviceId, MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        String name = String.valueOf(new Date().getTime());
        name = deviceId + "x" + name;

        // 获取扩展名
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        // /upload/ + name ".jpg" 全路径
        // 相对路径
        String path = "/upload/" + name;
        // 上传路径
        //  String url = request.getSession().getServletContext().getRealPath("/") + path;
        // String url = System.getProperties().getProperty("user.home")+path;
        String url = "C:" + path;
        //上传文件的方法

        File _file = new File(url).getParentFile();
        if (!_file.exists()) {
            _file.mkdirs();
        }
        file.transferTo(new File(url));
        return name;
    }


    /**
     * 下载操作
     *
     * @param deviceId 设备ID
     * @param path     文件全路径
     * @return
     */
    @RequestMapping(value = "downloadFile", method = RequestMethod.GET)
    public Object downloadFile(Long deviceId, String path) {
        path = updatePath(path, deviceId);
        path = path.substring(0, path.length() - 1);
        sdFileService.saveDownloadFileCommand(deviceId, path);
        return new ResponseBody(Constants.SUCCESS_CODE);

    }

    /**
     * 下载文件
     *
     * @param deviceId 设备ID
     * @param path     文件路径
     * @param filename 文件名
     * @return
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public Object download(Long deviceId, String path, String filename, HttpServletResponse response, HttpServletRequest request) {
        Object file = sdFileService.getFile(deviceId, path, filename);
        OutputStream os = null;
        if (file != null) {
            try {
                //处理文件中文名乱码问题
                String userAgent = request.getHeader("User-Agent");
                byte[] filenameBytes = userAgent.contains("MSIE") ? filename.getBytes()
                        : filename.getBytes(StandardCharsets.UTF_8);
                filename = new String(filenameBytes, StandardCharsets.ISO_8859_1);

                response.setCharacterEncoding("utf-8");
                response.setContentType("multipart/form-data");
                response.setHeader("Content-Disposition", "attachment;fileName="
                        + filename);
                byte[] bytes = (byte[]) file;
                os = response.getOutputStream();
                os.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return new ResponseBody(Constants.EMPTY_ARRAY);
        }
        return null;
    }

    @RequestMapping(value = "isExistFile", method = RequestMethod.GET)
    public Object isExistFile(Long deviceId, String path, String filename) {
        Object file = sdFileService.getFile(deviceId, path, filename);
        if (file != null)
            return new ResponseBody(Constants.SUCCESS_CODE);
        else
            return new ResponseBody(Constants.EMPTY_ARRAY);
    }

    private String updatePath(String path, Long deviceId) {
        String oldPath = null;
        SdFile sdFile = sdFileService.findByDeviceIdFirst(deviceId);
        if (sdFile != null)
            oldPath = sdFile.getPath();

        if ("/storage/usbotg".equals(oldPath)) {
            path = "/storage" + path;
        } else {
            path = "/storage/sdcard0" + path;
        }
        return path;
    }

    /**
     * 删除操作
     *
     * @param deviceId 设备ID
     * @param path     文件全路径
     * @return
     */
    @RequestMapping(value = "deleteFile", method = RequestMethod.GET)
    public Object deleteFile(Long deviceId, String path) {

        path = path.substring(0, path.length() - 1);
        String[] strings = path.split("#");
        String newPath = "";
        for (String s : strings) {
            newPath = updatePath(s, deviceId) + "#";
        }
        newPath = newPath.substring(0, newPath.length() - 1);
        sdFileService.saveDeleteFileCommand(deviceId, newPath);
        return new ResponseBody(Constants.SUCCESS_CODE);

    }

    /**
     * 查询操作
     *
     * @param deviceId 设备ID
     * @param path     文件全路径
     * @return
     */
    @RequestMapping(value = "findFile", method = RequestMethod.GET)
    public Object findFile(Long deviceId, String path) {

        path = path.substring(0, path.length() - 1);
        String[] strings = path.split("#");
        String newPath = "";
        for (String s : strings) {
            newPath = updatePath(s, deviceId) + "#";
        }
        newPath = newPath.substring(0, newPath.length() - 1);
        sdFileService.saveFindFileCommand(deviceId, newPath);
        return new ResponseBody(Constants.SUCCESS_CODE);

    }

    /**
     * 获取操作日志
     *
     * @return
     */
    @RequestMapping(value = "getParameterLog", method = RequestMethod.GET)
    public Object getParameterLog(Long deviceId, int pageNo, int pageSize, String startTime, String endTime) throws Exception {
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";
        PageQuery parameterLogs = sdFileService.getParameterLog(deviceId, DateUtils.stringToTime(startTime), DateUtils.stringToTime(endTime), pageNo, pageSize);
        //获取网络信息
        NodeInfo nodeInfo = deviceService.getSignalByDeviceId(deviceId);
        Map map = new HashMap();
        if (nodeInfo != null) {
            map.put("signalType", nodeInfo.getNetprovider() + nodeInfo.getNetworktype());
        } else {
            map.put("signalType", "");
        }
        return new ResponseBody(Constants.SUCCESS_CODE, map, parameterLogs);
    }

    /**
     * 关机操作
     *
     * @param deviceId
     * @return
     */
    @RequestMapping(value = "/powerOff", method = RequestMethod.GET)
    public Object powerOff(Long deviceId) {
        sdFileService.powerOff(deviceId);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 是否远程操作
     *
     * @param deviceId
     * @param status   1，是  0，否
     * @return
     */
    @RequestMapping(value = "/isRemote", method = RequestMethod.GET)
    public Object isRemote(Long deviceId, int status) {
        sdFileService.saveIsRemoteCommand(deviceId, status);

        return new ResponseBody(Constants.SUCCESS_CODE);
    }
}
