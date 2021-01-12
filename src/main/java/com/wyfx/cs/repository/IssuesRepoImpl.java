package com.wyfx.cs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/12/13.
 */
@Component
public class IssuesRepoImpl {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private IssuesRepo issuesRepo;


    public Long selectCount(boolean flag, String type, String severity, Long scanListId) {
        Query query;
        String sql;
        if (flag) {
            sql = "select count(*) from cs_issues where scan_list_id = ?1 and type = ?2 and  severity = ?3";
            query = em.createNativeQuery(sql);
            query.setParameter(1, scanListId);
            query.setParameter(2, type);
            query.setParameter(3, severity);
        } else {
            sql = "select count(*) from cs_issues where scan_list_id = ?1 and type = ?2";
            query = em.createNativeQuery(sql);
            query.setParameter(1, scanListId);
            query.setParameter(2, type);
        }
        List<BigInteger> resultList = query.getResultList();

        return resultList.get(0).longValue();
    }

    public List selectIssues(boolean flag, String type, String severity, Long scanListId) {
        Query query;
        String sql;
        if (flag) {
            sql = "select id,message from cs_issues where type = ?1 and  severity = ?2";
            query = em.createNativeQuery(sql);
            query.setParameter(1, type);
            query.setParameter(2, severity);
        } else {
            sql = "select id,message from cs_issues where type = ?1";
            query = em.createNativeQuery(sql);
            query.setParameter(1, type);
        }
        List<Object[]> resultList = query.getResultList();
        List<Map> mapList = new ArrayList<>();
        for (Object[] objects : resultList) {
            Map map = new HashMap();
            map.put("id", objects[0]);
            map.put("message", objects[1]);
            mapList.add(map);
        }
        return mapList;

    }

    public List getTop10OrList(Long scanListId, String s) {

        String sql;
        if (s.equals("top10")) {
            sql = "SELECT message,severity,count(message) c FROM cs_issues where scan_list_id = ?1 GROUP BY message,severity ORDER BY c DESC limit 0 ,10";
        } else {
            sql = "SELECT message,severity,count(message) c FROM cs_issues where scan_list_id = ?1 GROUP BY message,severity ORDER BY c DESC";
        }
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, scanListId);
        List<Object[]> resultList = query.getResultList();
        return resultList;
    }


}
