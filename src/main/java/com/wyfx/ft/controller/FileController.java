package com.wyfx.ft.controller;

import com.alibaba.fastjson.JSONArray;
import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.model.Type;
import com.wyfx.ft.model.vo.FileInfoVo;
import com.wyfx.ft.service.FileService;
import com.wyfx.ft.utils.FileUtils;
import com.wyfx.ft.utils.IpUtils;
import com.wyfx.ft.utils.LocationUtils;
import com.wyfx.ft.utils.ZipUtils;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.DateUtils;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipOutputStream;


/**
 * Author: liuxingyu
 * DATE: 2017-07-17.16:29
 * description:文件管理
 * version:
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 多文件上传
     *
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(MultipartFile file, HttpServletRequest request, String uuid) throws InterruptedException {
        //获取上传文件
        System.err.println("upload");
        //保存文件
        if (file != null) {
            FileInfo fileInfo = new FileInfo();
            String filename = file.getOriginalFilename();
            fileInfo.setFileName(filename);
            String path = FileUtils.saveFile(request, file);
            fileInfo.setPath(path);
            FileInfo info = null;
            if (uuid != null && !"".equals(uuid)) {
                //标记时，获取城市
                //获取请求的IP
                String ip = IpUtils.getIpAddress(request);
                //获取位置信息
                LocationUtils.LocationClass locationClass = LocationUtils.getLocation(ip);
                String city = locationClass.city;
                fileService.saveUuidAndPath(uuid, fileInfo, city);
                return new ResponseBody(Long.valueOf(Constants.SUCCESS_CODE), uuid);
            } else {

                info = fileService.save(fileInfo);
                return new ResponseBody(Constants.SUCCESS_CODE, info);
            }

        }
        //查询未标记的文件
        return null;

    }

    /***
     **异常处理
     ***/
    @ExceptionHandler(Exception.class)
    public @org.springframework.web.bind.annotation.ResponseBody
    Object ExceptionHandler(Exception exceededException) {
        System.err.println("Exception");
        if (exceededException != null && (exceededException.getCause() instanceof MaxUploadSizeExceededException)) {
            return new ResponseBody(Constants.EMPTY_ARRAY);
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
        List<Type> fileTypeVoList = fileService.getFileType();
        return new ResponseBody(Constants.SUCCESS_CODE, fileTypeVoList);

    }

    /**
     * 查询所有标记文件 分页
     *
     * @return
     */
    @RequestMapping(value = "/getAllFileSign", method = RequestMethod.GET)
    public Object getAllFileSign(int pageNo, int pageSize) throws Exception {
        PageQuery fileInfoVos = fileService.getAllFileSign(pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, fileInfoVos);
    }

    /**
     * 按条件查询追踪标记文件 分页
     *
     * @param fileTypeId 文件格式id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    @RequestMapping(value = "/getAllFileSignByCondition", method = RequestMethod.GET)
    public Object getAllFileSignByCondition(Long fileTypeId, String startTime, String endTime, int pageNo, int pageSize) throws Exception {
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";
        PageQuery fileInfoVos = fileService.getAllFileSignByCondition(fileTypeId, DateUtils.stringToDate(startTime), DateUtils.stringToDate(endTime), pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, fileInfoVos);
    }

    /**
     * 按条件查询标记文件 分页
     *
     * @param fileTypeId 文件格式id
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    @RequestMapping(value = "/getAllFileSignOrWatermarkByCondition", method = RequestMethod.GET)
    public Object getAllFileSignOrWatermarkByCondition(Long fileTypeId, String startTime, String endTime, int pageNo, int pageSize) throws Exception {
        if (startTime != null && !"".equals(startTime))
            startTime = startTime + " 00:00:00";
        if (endTime != null && !"".equals(endTime))
            endTime = endTime + " 23:59:59";
        PageQuery fileInfoVos = fileService.getAllFileSignOrWatermarkByCondition(fileTypeId, DateUtils.stringToDate(startTime), DateUtils.stringToDate(endTime), pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, fileInfoVos);
    }

    /**
     * 删除文件
     *
     * @param fileId 文件ID
     * @return
     */
    @RequestMapping(value = "/delFile", method = RequestMethod.GET)
    public Object delFile(Long fileId) {
        fileService.delFile(fileId);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 多文件标记
     *
     * @param fileIds 多个文件ID
     * @param tag     标签
     * @param remark  备注
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signFile", method = RequestMethod.GET)
    public Object signFile(String text, String fileIds, String tag, String remark, String params, HttpServletRequest request) throws Exception {
        //标记时，获取城市
        //获取请求的IP
        String ip = IpUtils.getIpAddress(request);
        //获取位置信息
        LocationUtils.LocationClass locationClass = LocationUtils.getLocation(ip);
        String city = locationClass.city;
        //文件标记
        fileService.signFile(fileIds, tag, remark, params, city, text);
        //返回文件标记结果
        List<FileInfoVo> fileInfoVos = fileService.getFileByIds(fileIds);
        return fileInfoVos;
    }

    /**
     * 文件下载
     *
     * @param fileIds  多个文件id
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downLoadFile", method = RequestMethod.GET)
    public Object downLoadFile(String fileIds, HttpServletResponse response, HttpServletRequest request) throws IOException {

        JSONArray jsonArray = JSONArray.parseArray(fileIds);
        //下载文件的个数为0，就直接返回
        if (jsonArray == null || jsonArray.size() == 0)
            return null;

        if (jsonArray.size() > 0) {
//            //下载单个文件
//            OutputStream os = null;
//            try{
//                os = response.getOutputStream();
//                Long fileId = jsonArray.getLong(0);
//                FileInfo fileInfo = fileService.getFileById(fileId);
//                File file = new File(fileInfo.getPath());
//                if(file.exists()){
//                    response.reset();
//                    String fileName = fileInfo.getFileName();
//                    //处理文件中文名乱码问题
//                    String userAgent = request.getHeader("User-Agent");
//                    byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes()
//                            : fileName.getBytes("UTF-8");
//                     fileName = new String(bytes, "ISO-8859-1");
//                    response.setHeader("Content-Disposition", "attachment;filename="+fileName);
//                    response.setContentType("application/octet-stream; charset=utf-8");
//
//                    os.write(org.apache.commons.io.FileUtils.readFileToByteArray(file));
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//                os.close();
//            }
//
//        }else {
            //下载多个文件，将文件压缩zip格式
            String zipName = "myfile.zip";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
            for (int i = 0; i < jsonArray.size(); i++) {
                try {

                    FileInfo fileInfo = fileService.getFileById(jsonArray.getLong(i));
                    ZipUtils.doCompress(fileInfo.getPath(), out);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            out.close();

        }
        return null;
    }

    /**
     * 获取最新文件
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFileNewest", method = RequestMethod.GET)
    public Object getFileNewest() throws Exception {
        FileInfoVo fileInfoVo = fileService.getFileNewest();
        if (fileInfoVo != null)
            return new ResponseBody(Constants.SUCCESS_CODE, fileInfoVo);
        else
            return new ResponseBody(Constants.NO_FILE_CODE);
    }

    /**
     * 生成唯一id
     *
     * @return
     */
    @RequestMapping(value = "/getUuid", method = RequestMethod.GET)
    public Object getUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return new ResponseBody(Long.valueOf(Constants.SUCCESS_CODE), uuid);
    }


}
