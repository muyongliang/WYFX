package com.wyfx.ft.utils;

import com.wyfx.ft.model.DataInfo;
import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.repository.DataRepo;
import com.wyfx.ft.repository.FileRepo;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/25.
 */
@Transactional
public class Sk implements Runnable {
    private final DataRepo dataRepo;
    private final FileRepo fileRepo;
    private final Socket socket;

    public Sk(FileRepo fileRepo, DataRepo dataRepo, Socket socket) {
        this.fileRepo = fileRepo;
        this.dataRepo = dataRepo;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //设置连接超时属性，60秒未收到数据，则连接超时
            socket.setKeepAlive(true);
            //socket.setOOBInline(true);
            socket.setSoTimeout(120000);
        } catch (SocketException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            DataInfo dataInfo = new DataInfo();
            System.out.println("连接成功....");

            //设置时间
            dataInfo.setTime(new Date());

            BufferedReader br = new BufferedReader(new
                    InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            //发送信息
            String s = br.readLine();
            String uuid = s.substring(4);
//            while ((s = br.readLine()) != null){
//                System.err.println(s);
//            }
            FileInfo fileInfo = fileRepo.findByUuid(uuid);
            if (fileInfo != null) {
                dataInfo.setFileInfo(fileInfo);
                String ip = socket.getInetAddress().toString();
                ip = ip.substring(1);
                dataInfo.setIp(ip);
                //获取位置信息
                LocationUtils.LocationClass locationClass = LocationUtils.getLocation(ip);
                dataInfo.setIsp(locationClass.isp);
                dataInfo.setCountry(locationClass.country);
                dataInfo.setRegion(locationClass.region);
                if (locationClass.city == null || "".equals(locationClass.city)) {

                    dataInfo.setCity(locationClass.region);
                } else {
                    dataInfo.setCity(locationClass.city);
                }
                dataRepo.save(dataInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
