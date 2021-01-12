package com.wyfx.ft.controller;


import com.wyfx.ft.model.DataInfo;
import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.repository.DataRepo;
import com.wyfx.ft.repository.FileRepo;
import com.wyfx.ft.utils.IpUtils;
import com.wyfx.ft.utils.LocationUtils;
import com.wyfx.ft.utils.UaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

@RestController
public class ImgController {

    static final byte[] bmpByte = new byte[]{
            0x42, 0x4d, 0x42, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x3e, 0x00, 0x00, 0x00, 0x28, 0x00,
            0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00,
            0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x04, 0x00, 0x00, 0x00, (byte) 0xc4, 0x0e,
            0x00, 0x00, (byte) 0xc4, 0x0e, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, (byte) 0x80, 0x00,
            0x00, 0x00
    };
    private static final Logger logger = LoggerFactory.getLogger(ImgController.class);
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private DataRepo dataRepo;

    public static void main(String[] args) {
        byte[] data = new byte[]{
                0x42, 0x4d, 0x42, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x3e, 0x00, 0x00, 0x00, 0x28, 0x00,
                0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00,
                0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x04, 0x00, 0x00, 0x00, (byte) 0xc4, 0x0e,
                0x00, 0x00, (byte) 0xc4, 0x0e, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff, 0x00, (byte) 0x80, 0x00,
                0x00, 0x00
        };
        String path = "E:/aaaa.jpeg";
        System.err.println(path);
        if (data.length < 3 || path.equals("")) return;
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/img/{filename:^[0-9a-z]{32}.jpg$}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename, HttpServletRequest request) {

        logger.debug("method : " + request.getMethod());
        if (!request.getMethod().equals("GET")) {
            return ResponseEntity.ok().build();
        }
        //新建实体，用于存储回传数据
        DataInfo dataInfo = new DataInfo();

        String uuid = filename.substring(0, filename.indexOf('.'));
        logger.debug("uuid:" + uuid);
        //通过UUID查询文件
        FileInfo fileInfo = fileRepo.findByUuid(uuid);
        dataInfo.setFileInfo(fileInfo);
        //设置头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.IMAGE_JPEG);
        //通过头获取信息
        String ua = request.getHeader("User-Agent");
        logger.debug("download ua:" + ua);

        if (fileInfo.getStatus() == 0) {
            logger.info("Invalid uuid:" + uuid + ", User-Agent:" + ua);
            return new ResponseEntity<byte[]>(bmpByte, headers, HttpStatus.CREATED);
        }
        UaUtils.UaClass uaClass = UaUtils.parseUa(ua);

        logger.debug("browser:" + uaClass.browser);
        logger.debug("browserVersion:" + uaClass.browserVersion);
        logger.debug("system:" + uaClass.system);
        logger.debug("systemVersion:" + uaClass.systemVersion);
        logger.debug("device:" + uaClass.device);
        //数据封装
        dataInfo.setBrowser(uaClass.browser);
        dataInfo.setBrowserVersion(uaClass.browserVersion);
        dataInfo.setSystem(uaClass.system);
        dataInfo.setSystemVersion(uaClass.systemVersion);
        dataInfo.setDevice(uaClass.device);
        //获取请求的IP
        String ip = IpUtils.getIpAddress(request);
        logger.debug("download ip:" + ip);
        dataInfo.setIp(ip);
        //获取位置信息
        LocationUtils.LocationClass locationClass = LocationUtils.getLocation(ip);
        logger.debug("isp:" + locationClass.isp);
        logger.debug("country:" + locationClass.country);
        logger.debug("region:" + locationClass.region);
        logger.debug("city:" + locationClass.city);
        dataInfo.setIsp(locationClass.isp);
        dataInfo.setCountry(locationClass.country);
        dataInfo.setRegion(locationClass.region);
        if (locationClass.city == null || "".equals(locationClass.city)) {

            dataInfo.setCity(locationClass.region);
        } else {
            dataInfo.setCity(locationClass.city);
        }
        //设置时间
        dataInfo.setTime(new Date());
        try {
            dataRepo.save(dataInfo);
            return new ResponseEntity<>(bmpByte, headers, HttpStatus.OK);

        } catch (Exception e) {
            logger.debug("error:" + e.toString());
            return ResponseEntity.notFound().build();
        }


    }


}
