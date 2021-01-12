package com.wyfx.cs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyfx.cs.model.Issues;
import com.wyfx.cs.model.ScanList;
import com.wyfx.cs.model.vo.IssuesVo;
import com.wyfx.cs.model.vo.ScanListDetails;
import com.wyfx.cs.repository.IssuesRepo;
import com.wyfx.cs.repository.IssuesRepoImpl;
import com.wyfx.cs.repository.ScanListRepo;
import com.wyfx.cs.service.IssuesService;
import com.wyfx.cs.utils.Constants;
import com.wyfx.cs.utils.HttpRequestUtils;
import com.wyfx.upms.utils.ModelChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/12/13.
 */
@Service
@Transactional
public class IssuesSrvImpl implements IssuesService {
    @Autowired
    private IssuesRepoImpl issuesRepoImpl;
    @Autowired
    private ScanListRepo scanListRepo;
    @Autowired
    private IssuesRepo issuesRepo;

    @Override
    public void saveIssues(String projectKey) {
        ScanList scanList = scanListRepo.findByProjectKey(projectKey);
        JSONObject jsonObject = HttpRequestUtils.httpGet(Constants.API_ISSUES_SEARCH);
        JSONArray jsonArray = jsonObject.getJSONArray("issues");
        for (int i = 0; i < jsonArray.size(); i++) {
            Issues issues = new Issues();
            JSONObject object = jsonArray.getJSONObject(i);
            String severity = object.getString("severity");
            String message = object.getString("message");
            String type = object.getString("type");
            int line = -1;
            try {
                line = object.getInteger("line");
            } catch (Exception e) {

            }

            if (!"CODE_SMELL".equals(type) && !"MAJOR".equals(severity) && !"MINOR".equals(severity) && !"CRITICAL".equals(severity))
                break;
            if ("CODE_SMELL".equals(type))
                severity = type;

            String component = object.getString("component");

            issues.setSeverity(severity);
            issues.setMessage(message);
            issues.setType(type);
            issues.setComponent(component);
            issues.setScanList(scanList);
            issues.setLine(line);
            issuesRepo.save(issues);
        }

    }

    @Override
    public Map serachIssues(Long scanListId) throws Exception {
        Map map = new HashMap();
        //扫描清单详情
        ScanList scanList = scanListRepo.findOne(scanListId);
        ScanListDetails scanListDetails = ModelChange.changeEntity(ScanListDetails.class, scanList);
        map.put("scanList", scanListDetails);
        //CRITICAL 高  MAJOR 中 MINOR 低 CODE_SMELL 代码质量
        //安全状况详情
        Map securityStatus = new HashMap();
        saveMap(securityStatus, true, "VULNERABILITY", "CRITICAL", scanListId);
        saveMap(securityStatus, true, "VULNERABILITY", "MAJOR", scanListId);
        saveMap(securityStatus, true, "VULNERABILITY", "MINOR", scanListId);
        saveMap(securityStatus, false, "CODE_SMELL", "", scanListId);
        map.put("securityStatus", securityStatus);

        //安全隐患分布
        Long CRITICAL = issuesRepoImpl.selectCount(true, "VULNERABILITY", "CRITICAL", scanListId);
        Long MAJOR = issuesRepoImpl.selectCount(true, "VULNERABILITY", "MAJOR", scanListId);
        Long MINOR = issuesRepoImpl.selectCount(true, "VULNERABILITY", "MINOR", scanListId);
        Long CODE_SMELL = issuesRepoImpl.selectCount(false, "CODE_SMELL", "", scanListId);
//
        List percentList = new ArrayList();
        percentList.add(CRITICAL);
        percentList.add(MAJOR);
        percentList.add(MINOR);
        percentList.add(CODE_SMELL);

        map.put("percent", percentList);

        //安全隐患top10
        List<Object[]> list = issuesRepoImpl.getTop10OrList(scanListId, "top10");
        for (Object[] objects : list) {
            String s = objects[1].toString();
            if ("CRITICAL".equals(s))
                objects[1] = "#fd505a";
            if ("MAJOR".equals(s))
                objects[1] = "#fdc958";
            if ("MINOR".equals(s))
                objects[1] = "#69cc74";
            if ("CODE_SMELL".equals(s))
                objects[1] = "#50bbed";
        }
        map.put("top10", list);

        //基桑图
        List<Object[]> orList = issuesRepoImpl.getTop10OrList(scanListId, "list");

        Map sankey = new HashMap();
        List links = new ArrayList();
        List nodes = new ArrayList();
        for (Object[] objects : orList) {
            for (int i = 0; i < objects.length; i++) {
                String s = objects[1].toString();
                if ("CRITICAL".equals(s))
                    objects[1] = "高";
                if ("MAJOR".equals(s))
                    objects[1] = "中";
                if ("MINOR".equals(s))
                    objects[1] = "低";
                if ("CODE_SMELL".equals(s))
                    objects[1] = "代码质量";
                Map nodesMap = new HashMap();
                nodesMap.put("name", objects[i]);
                nodes.add(nodesMap);
                if (i != objects.length - 1) {
                    Map linksMap = new HashMap();
                    linksMap.put("source", objects[i]);
                    linksMap.put("target", objects[i + 1]);
                    linksMap.put("value", 10);
                    links.add(linksMap);
                }

            }
        }
        sankey.put("links", links);
        sankey.put("nodes", nodes);

        map.put("sankey", sankey);


        return map;
    }

    @Override
    public Map serachIssuesAndCode(Long scanListId) throws Exception {
        Map map = new HashMap();
        //扫描清单详情
        ScanList scanList = scanListRepo.findOne(scanListId);
        ScanListDetails scanListDetails = ModelChange.changeEntity(ScanListDetails.class, scanList);
        map.put("scanList", scanListDetails);
        List<IssuesVo> critical = getIssuesVos(scanList, "CRITICAL");
        List<IssuesVo> major = getIssuesVos(scanList, "MAJOR");
        List<IssuesVo> minor = getIssuesVos(scanList, "MINOR");
        List<IssuesVo> code_smell = getIssuesVos(scanList, "CODE_SMELL");

        map.put("CRITICAL", critical);
        map.put("MAJOR", major);
        map.put("MINOR", minor);
        map.put("CODE_SMELL", code_smell);

        return map;
    }

    @Override
    public Map getCodeByIssuesId(Long issuesId) throws UnsupportedEncodingException {
        Issues issues = issuesRepo.findOne(issuesId);

        Map map = new HashMap();
        String component = issues.getComponent();
        StringBuffer stringBuffer = new StringBuffer(Constants.API_SOURCES_LINES);
        stringBuffer.append("?key=" + URLEncoder.encode(component, "UTF-8"));
        stringBuffer.append("&from=1");
        stringBuffer.append("&to=100001");

        JSONObject jsonObject = HttpRequestUtils.httpGet(new String(stringBuffer));

        JSONArray sources = jsonObject.getJSONArray("sources");

        File file = new File("E:/aa.html");
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            for (int i = 0; i < sources.size(); i++) {
                String code = sources.getJSONObject(i).getString("code");
                code = code.replace(" ", "&nbsp");
                code = code + "<br>";
                //  code = URLEncoder.encode(code,"UTF-8");
                writer.write(code);
                writer.newLine();
            }

            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        map.put("component", component);
        map.put("line", issues.getLine());
        map.put("sources", sources);

        return map;
    }

    public List<IssuesVo> getIssuesVos(ScanList scanList, String severity) throws Exception {
        List<Issues> issues = issuesRepo.findByScanListAndSeverity(scanList, severity);
        List<IssuesVo> issuesVos = ModelChange.changeList(IssuesVo.class, issues);
        return issuesVos;
    }

    public void savePercentList(List percentList, String severity, Long num) {
        Map map = new HashMap();
        map.put("value", num);
        map.put("name", severity);
        percentList.add(map);
    }

    public void saveMap(Map securityStatus, boolean flag, String type, String severity, Long scanListId) {
        Map map = new HashMap();

        Long count = issuesRepoImpl.selectCount(flag, type, severity, scanListId);
        map.put("count", count);
        List list = issuesRepoImpl.selectIssues(flag, type, severity, scanListId);
        map.put("list", list);
        if (flag) {
            securityStatus.put(severity, map);
        } else {
            securityStatus.put(type, map);
        }
//        return map;

    }
}
