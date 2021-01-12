package com.wyfx.ft.controller;

import com.alibaba.fastjson.JSONObject;
import com.wyfx.ft.model.FwFileInfo;
import com.wyfx.ft.model.FwType;
import com.wyfx.ft.service.FwFileService;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Created by liu on 2017/8/18.
 * 文件还原
 */
@RestController
@RequestMapping(value = "/fwFile")
public class FwFileController {
    @Autowired
    private FwFileService fwFileService;

    /**
     * 防火墙拦截文件 分页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllFwFileOrSign", method = RequestMethod.POST)
    public Object getAllFwFile(@RequestBody JSONObject jsonObject) throws Exception {
        int pageNo = jsonObject.getInteger("pageNo");
        int pageSize = jsonObject.getInteger("pageSize");
        int flag = jsonObject.getInteger("flag");
        String filename = jsonObject.getString("filename");
        String ip = jsonObject.getString("ip");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        String network = jsonObject.getString("network");
        Long fwTypeId = jsonObject.getLong("fwTypeId");
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";
        PageQuery pageQuery = fwFileService.getAllFwFile(pageNo, pageSize, flag, filename, fwTypeId, ip, startTime, endTime, network);
        return new ResponseBody(Constants.SUCCESS_CODE, pageQuery);
    }

    /**
     * 判断文件是否存在
     *
     * @param fwFileId
     */
    @RequestMapping(value = "/isExistFile", method = RequestMethod.GET)
    public Object isExistFile(Long fwFileId) {
        boolean flag = fwFileService.isExistFile(fwFileId);
        if (flag)
            return new ResponseBody(Constants.SUCCESS_CODE);
        return new ResponseBody(Constants.EMPTY_ARRAY);
    }

    /**
     * 查看原文件
     *
     * @param fileId
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downLoadFwFile", method = RequestMethod.GET)
    public Object downLoadFwFile(Long fileId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            FwFileInfo fileInfo = fwFileService.getFileByFwFileId(fileId);
            File file = new File(fileInfo.getPath());
            if (file.exists()) {
                response.reset();
                String fileName = fileInfo.getFilename();
                //处理文件中文名乱码问题
                String userAgent = request.getHeader("User-Agent");
                byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes()
                        : fileName.getBytes(StandardCharsets.UTF_8);
                fileName = new String(bytes, StandardCharsets.ISO_8859_1);
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setContentType("application/octet-stream; charset=utf-8");

                os.write(org.apache.commons.io.FileUtils.readFileToByteArray(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            os.close();
        }
        return null;
    }

    /**
     * 查询文件类型
     *
     * @return
     */
    @RequestMapping(value = "/getFileType", method = RequestMethod.GET)
    public Object getFileType() throws Exception {
        List<FwType> fileTypeVoList = fwFileService.getFileType();
        return new ResponseBody(Constants.SUCCESS_CODE, fileTypeVoList);

    }

    /**
     * 修改备注
     *
     * @param id
     * @param remark 备注
     * @return
     */
    @RequestMapping(value = "/updateRemark", method = RequestMethod.POST)
    public Object updateRemark(@RequestBody JSONObject jsonObject) {

        Long id = jsonObject.getLong("id");
        String remark = jsonObject.getString("remark");
        if (remark != null && remark.length() > 50)
            return new ResponseBody(Constants.EMPTY_ARRAY);
        fwFileService.updateRemark(id, remark);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 获取文件数量和百分比
     *
     * @return
     */
    @RequestMapping(value = "/getFwFileCountAndPercent", method = RequestMethod.GET)
    public Object getFwFileCountAndPercent() {
        Map map = fwFileService.getFwFileCountAndPercent();
        return new ResponseBody(Constants.SUCCESS_CODE, map);
    }

    @RequestMapping(value = "/getProtocol", method = RequestMethod.GET)
    public Object getProtocol() {
        String protocol = "http,smtp,ftp";
        String[] strings = protocol.split(",");
        return new ResponseBody(Constants.SUCCESS_CODE, strings);
    }


}
