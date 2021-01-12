package com.wyfx.ft.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyfx.ft.model.DataInfo;
import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.repository.DataRepo;
import com.wyfx.ft.repository.FileRepo;
import com.wyfx.upms.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class TimerTask {
    private static final int num = 60069;
    private final static int count = 500;
    private static final String[] strings = new String[]{"电信", "移动", "联通"};
    @Autowired
    FileRepo fileRepo;
    @Autowired
    DataRepo dataRepo;

    public static void main(String[] args) throws Exception {
//        JSONObject jsonObject = JSONObject.parseObject(Constants.CITY_JSON);
//        JSONArray jsonArray = jsonObject.getJSONArray("cities");
//        JSONObject jsonObject1 = jsonArray.getJSONObject(num);
//        jsonObject1.getString("n");
        new TimerTask().read();


    }

    /**
     * 获取随机日期
     *
     * @param beginDate 起始日期，格式为：yyyy-MM-dd
     * @param endDate   结束日期，格式为：yyyy-MM-dd
     * @return
     */

    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    //  @Scheduled(cron = "0/60 * * * * ?")
    public void run() throws Exception {
//        ReadFTPFile readFTPFile = new ReadFTPFile();
//        //读取http日志文件
//        readFTPFile.readConfigFileForFTP(Constants.HTTP_PATH,Constants.HTTP_NAME,Constants.LOCAL_LOG_PATH);
//        Thread.sleep(1000);
//        //读取smtp日志文件
//        readFTPFile.readConfigFileForFTP(Constants.SMTP_PATH,Constants.SMTP_NAME,Constants.LOCAL_LOG_PATH);
//        Thread.sleep(1000);
//        //读取ftp日志文件
//        readFTPFile.readConfigFileForFTP(Constants.FTP_PATH,Constants.FTP_NAME,Constants.LOCAL_LOG_PATH);

        //读取本地日志
        try {
            readLogs();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void read() {
        for (int i = 0; i < 500; i++) {
            List list = new ArrayList();
            String[] split = Constants.COUNTRY_JSON.split("],");
            for (int j = 0; j < split.length; j++) {
                String s = split[j];
                s = s + "]";
                s = s.replace("\n", "");
                int lastIndexOf = s.lastIndexOf("\"");
                s = s.substring(1, lastIndexOf);
                list.add(s);
            }
            DataInfo dataInfo = new DataInfo();
            Random random = new Random();
            int nextInt = random.nextInt(list.size());

            Date date = randomDate("2017-05-01", "2017-11-01");
            System.err.println(date);
            String country = list.get(nextInt).toString();
            dataInfo.setCountry(country);
            if (country.equals("中国")) {
                JSONObject jsonObject = JSONObject.parseObject(Constants.AREA_JSON);
                JSONArray jsonArray = jsonObject.getJSONArray("provinces");
                Random r = new Random();
                int ii = r.nextInt(jsonArray.size());
                JSONObject object = jsonArray.getJSONObject(ii);
                String provinceName = object.getString("provinceName");
                if (provinceName.endsWith("省") || provinceName.endsWith("市")) {
                    provinceName = provinceName.substring(0, provinceName.length() - 1);
                }
                System.err.println(provinceName);
                JSONArray citys = object.getJSONArray("citys");
                int nextInt1 = r.nextInt(citys.size());
                String citysName = citys.getJSONObject(nextInt1).getString("citysName");
                if (citysName.endsWith("市")) {
                    citysName = citysName.substring(0, citysName.length() - 1);
                }

                JSONObject json = JSONObject.parseObject(Constants.CITY_JSON);
                JSONArray Array = json.getJSONArray("cities");
                boolean flag = true;
                for (int z = 0; z < Array.size(); z++) {
                    String n = Array.getJSONObject(z).getString("n");
                    if (citysName.equals(n)) {
                        flag = false;
                    }
                }
                if (flag) continue;
                dataInfo.setCity(citysName);
                dataInfo.setRegion(provinceName);
            }
            dataInfo.setTime(date);
            // dataInfo.setDevice("pc");
            // dataInfo.setSystem("Windows 10");
            //  dataInfo.setSystemVersion("NT 10.0");
            int anInt = random.nextInt(3);
            dataInfo.setIsp(strings[anInt]);
            String ip = random.nextInt(255) + "." + random.nextInt(255) + "." + random.nextInt(255) + "." + random.nextInt(255);
            System.err.println(ip);
            dataInfo.setIp(ip);
            FileInfo fileInfo = fileRepo.findOne(240l);
            dataInfo.setFileInfo(fileInfo);
            dataRepo.save(dataInfo);

        }
    }

    public void readLogs() {
        for (int j = 0; j < 500; j++) {
            DataInfo dataInfo = new DataInfo();
            JSONObject jsonObject = JSONObject.parseObject(Constants.AREA_JSON);
            JSONArray jsonArray = jsonObject.getJSONArray("provinces");
            Random r = new Random();
            int i = r.nextInt(jsonArray.size());
            JSONObject object = jsonArray.getJSONObject(i);
            String provinceName = object.getString("provinceName");
            if (provinceName.endsWith("省") || provinceName.endsWith("市")) {
                provinceName = provinceName.substring(0, provinceName.length() - 1);
            }
            System.err.println(provinceName);
            JSONArray citys = object.getJSONArray("citys");
            int nextInt = r.nextInt(citys.size());
            String citysName = citys.getJSONObject(nextInt).getString("citysName");
            if (citysName.endsWith("市")) {
                citysName = citysName.substring(0, citysName.length() - 1);
            }

            JSONObject json = JSONObject.parseObject(Constants.CITY_JSON);
            JSONArray Array = json.getJSONArray("cities");
            boolean flag = true;
            for (int z = 0; z < Array.size(); z++) {
                String n = Array.getJSONObject(z).getString("n");
                if (citysName.equals(n)) {
                    flag = false;
                }
            }
            if (flag) continue;
            System.err.println(citysName);
            Date date = randomDate("2017-05-01", "2017-11-01");
            System.err.println(date);
            dataInfo.setCity(citysName);
            dataInfo.setRegion(provinceName);
            dataInfo.setTime(date);
//            dataInfo.setCountry("中国");
//            dataInfo.setDevice("pc");
//            dataInfo.setSystem("Windows 10");
            dataInfo.setSystemVersion("NT 10.0");
            int anInt = r.nextInt(3);
            dataInfo.setIsp(strings[anInt]);
            String ip = r.nextInt(255) + "." + r.nextInt(255) + "." + r.nextInt(255) + "." + r.nextInt(255);
            System.err.println(ip);
            dataInfo.setIp(ip);
            FileInfo fileInfo = fileRepo.findOne(240l);
            dataInfo.setFileInfo(fileInfo);
            dataRepo.save(dataInfo);
        }
    }

}
