package com.wyfx.ft.repository;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyfx.ft.model.FileInfo;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by liu on 2017/8/9.
 */
@Component
public class FileDataRepoImpl {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private FileRepo fileRepo;

    public List getCityOrRegionAndCount(Long fileId, int flag, int pageSize, String startTime, String endTime) {
        if (fileId == null) {
            List<FileInfo> fileInfos = fileRepo.findByStatus(Constants.YES);
            fileId = fileInfos.get(fileInfos.size() - 1).getId();
        }

        //自定义sql查询
        String sql;
        Query query;
        if (startTime != null && endTime != null) {
            if (flag == 1) {
                sql = " select city,count(*) c from tfs_data_info where file_info_id = ?1 and country = ?2 and time <= ?3 and time >= ?4 group by city order by c DESC \n" +
                        " limit 0,";
            } else {
                sql = " select region,count(*) c from tfs_data_info where file_info_id = ?1 and country = ?2 and time <= ?3 and time >= ?4 group by region order by c DESC \n" +
                        " limit 0,";
            }
            query = em.createNativeQuery(sql + pageSize);
            query.setParameter(3, endTime);
            query.setParameter(4, startTime);
        } else {
            if (flag == 1) {
                sql = " select city,count(*) c from tfs_data_info where file_info_id = ?1 and country = ?2 group by city order by c DESC \n" +
                        " limit 0,";
            } else {
                sql = " select region,count(*) c from tfs_data_info where file_info_id = ?1 and country = ?2 group by region order by c DESC \n" +
                        " limit 0,";
            }
            query = em.createNativeQuery(sql + pageSize);
        }

        query.setParameter(1, fileId);
        query.setParameter(2, "中国");
        List<Object[]> resultList = query.getResultList();

        List returnList = new ArrayList();
        for (int i = resultList.size() - 1; i >= 0; i--) {
            Object[] object = resultList.get(i);
            Map map = new HashMap();
            map.put("area", object[0]);
            map.put("count", object[1]);
            returnList.add(map);
        }
        return returnList;

    }

    public Map getCityAndPercent(Long fileId, String startTime, String endTime, String type) {
        Map map = new HashMap();
        //1.保存初始位置
        FileInfo fileInfo = fileRepo.findById(fileId);
        //初始城市
        String city = fileInfo.getSignCity();
        Map initMap = new HashMap();
        initMap.put("name", city);
        //查询总数
        Query countQuery;
        String countSql = "select count(*) from tfs_data_info where file_info_id = ?1";
        if (startTime != null && endTime != null) {
            countSql = countSql + " and time <= ?2 and time >= ?3";
            countQuery = em.createNativeQuery(countSql);
            countQuery.setParameter(2, endTime);
            countQuery.setParameter(3, startTime);
        } else {
            countQuery = em.createNativeQuery(countSql);
        }
        countQuery.setParameter(1, fileId);
        List list = countQuery.getResultList();
        int count = Integer.parseInt(list.get(0).toString());

        String sql;
        Query query;
        if (startTime != null && endTime != null) {
            if ("china".equals(type)) {

                sql = " select city,count(*) c from tfs_data_info where file_info_id = ?1 and time <= ?2 and time >= ?3 and country = ?4 group by city order by c desc";
                query = em.createNativeQuery(sql);
                query.setParameter(4, "中国");
            } else {
                sql = " select country,count(*) c from tfs_data_info where file_info_id = ?1 and time <= ?2 and time >= ?3 group by country order by c desc";
                query = em.createNativeQuery(sql);
            }
            query.setParameter(2, endTime);
            query.setParameter(3, startTime);
        } else {
            if ("china".equals(type)) {

                sql = " select city,count(*) c from tfs_data_info where file_info_id = ?1 and country = ?2 group by city order by c desc";
                query = em.createNativeQuery(sql);
                query.setParameter(2, "中国");
            } else {
                sql = " select country,count(*) c from tfs_data_info where file_info_id = ?1 group by country order by c desc";
                query = em.createNativeQuery(sql);
            }

        }
        query.setParameter(1, fileId);
        List<Object[]> resultList = query.getResultList();
        //格式： '韶关': [113.7964,24.7028]
        Map cityMap = new HashMap();
        //格式： [{name:'北京'}, {name:'上海',value:95}]
        List returnList = new ArrayList();
        for (Object[] object : resultList) {
            if ("china".equals(type)) {

                cityMap = getLongitudeAndLatitude(object[0].toString(), cityMap);
            } else {
                cityMap = getLongitudeAndLatitudeByCountry(object[0].toString(), cityMap);
            }

            Object[] cityS = new Object[2];
            cityS[0] = initMap;
            int an = Integer.parseInt(object[1].toString());
            Map cMap = new HashMap();
            cMap.put("name", object[0]);
            cMap.put("value", 100);
            cityS[1] = cMap;
            returnList.add(cityS);
        }
        //初始位置的经纬度
        cityMap = getLongitudeAndLatitude(city, cityMap);
        map.put("count", count);
        map.put("city", cityMap);
        map.put("cityPercent", returnList);
        return map;

    }

    public Map getLongitudeAndLatitudeByCountry(String country, Map map) {
        if (country == null) return map;
        String[] split = Constants.COUNTRY_JSON.split("],");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            s = s + "]";
            s = s.replace("\n", "");
            if (s.indexOf(country) != -1) {
                String[] strings = s.split(":");
                String string = strings[1];
//                String[] strings1 = string.split(",");
                JSONArray jsonArray = JSONArray.parseArray(string);
                String replace = strings[0].replace("\"", "");
                map.put(replace, jsonArray);
            }
        }
        return map;

    }

    public Map getLongitudeAndLatitude(String city, Map map) {
        JSONObject jsonObject = JSONObject.parseObject(Constants.CITY_JSON);
        String cities = jsonObject.getString("cities");
        JSONArray jsonArray = JSONArray.parseArray(cities);
        if (city == null) return map;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String n = object.getString("n");
            if (city.equals(n)) {
                String g = object.getString("g");
                String substring = g.substring(0, g.indexOf("|"));
                String[] strings = substring.split(",");
                map.put(n, strings);
                break;

            }
        }
        return map;
    }


    public Map getCountByDay(Long fileId, String startTime, String endTime) {
        String sql = "SELECT DATE_FORMAT(time,'%Y-%m-%d') as t , count(*) as count FROM tfs_data_info tfs where file_info_id = ?3 " +
                "and time >=  ?1 and time <= ?2 GROUP BY  t";
        Query query = em.createNativeQuery(sql);
        if (startTime == null || "".equals(startTime) || endTime == null || "".equals(endTime)) {
//            FileInfo fileInfo = fileRepo.findById(fileId);
//            startTime = DateUtils.dateToString(fileInfo.getTime());
            startTime = "2017-07-01";
            endTime = DateUtils.dateToString(new Date());
        }
        query.setParameter(1, startTime);
        query.setParameter(2, endTime + "23:59:59");
        query.setParameter(3, fileId);
        List<Object[]> resultList = query.getResultList();
        Map map = new HashMap();
        if (resultList.size() == 0) {
            map.put("flag", true);
            return map;
        }
        map.put("flag", false);

        List<String> timeList = DateUtils.collectLocalDates(startTime, endTime);
        List countList = new ArrayList();
        for (String time : timeList) {
            boolean flag = true;
            for (Object[] object : resultList) {
                String s = object[0].toString();
                if (time.equals(s)) {
                    countList.add(object[1]);
                    flag = false;
                    break;
                }
            }
            if (flag) countList.add(0);
        }


        map.put("time", timeList);
        map.put("count", countList);
        return map;
    }


}
