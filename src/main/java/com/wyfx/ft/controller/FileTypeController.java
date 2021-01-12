package com.wyfx.ft.controller;

import com.alibaba.fastjson.JSONObject;
import com.wyfx.ft.model.FileType;
import com.wyfx.ft.model.vo.FileTypeVo;
import com.wyfx.ft.service.FileService;
import com.wyfx.ft.utils.FileUtils;
import com.wyfx.upms.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

/**
 * Created by liu on 2017/7/31.
 * 文件格式管理
 */
@RestController
@RequestMapping(value = "/fileType")
public class FileTypeController {
    @Autowired
    private FileService fileService;

    /**
     * 导入算法
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/importPSO", method = RequestMethod.POST)
    public Object importPSO(MultipartFile file, HttpServletRequest request) throws Exception {
        String langue = Constants.DEFAULT_LANGUE;
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            //保存文件，返回路径
            String path = FileUtils.saveFile(request, file);
//            //Linux下，给文件执行权限
//            String os = System.getProperty("os.name");
//            if (!os.toLowerCase().startsWith("win")) {
//                System.err.println(os);
//                Runtime.getRuntime().exec("chmod 755 " + path);
//
//            }
            String uuid;
            Class<?> aClass;
            URL url = new URL("file:" + path);
            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread()
                    .getContextClassLoader());
            try {
                aClass = myClassLoader.loadClass("com.example.wyfx.CacheConfiguration");
                Method method = aClass.getDeclaredMethod("getUuid", String.class);
                uuid = (String) method.invoke(null, langue);
            } catch (Exception e) {
                e.printStackTrace();
                //jar文件读取失败
                myClassLoader.close();
                FileUtils.deleteFileAndDirectory(path);
                return new ResponseBody(Long.valueOf(Constants.FAILED_CODE), BundleUtils.getValue(Constants.importErr));
            }
            //获取该算法的版本
            Method getVersion = aClass.getDeclaredMethod("getVersion", String.class);
            Double version = (Double) getVersion.invoke(null, langue);

            //算法类型
            Method getType = aClass.getDeclaredMethod("getType", String.class);
            String type = (String) getType.invoke(null, langue);

            //标记类全路径
            Method getCatalog = aClass.getDeclaredMethod("getCatalog", String.class);
            String catalog = (String) getCatalog.invoke(null, langue);

            //标记方法
            Method getMethod = aClass.getDeclaredMethod("getMethod", String.class);
            String method = (String) getMethod.invoke(null, langue);

            //水印类全路径
            Method getCatalogWatermark = aClass.getDeclaredMethod("getCatalogWatermark", String.class);
            String catalogWatermark = (String) getCatalogWatermark.invoke(null, langue);

            // 水印方法
            Method getMethodWatermark = aClass.getDeclaredMethod("getMethodWatermark", String.class);
            String methodWatermark = (String) getMethodWatermark.invoke(null, langue);

            //格式类型
            Method getSwf = aClass.getDeclaredMethod("getSwf", String.class);
            String swf = (String) getSwf.invoke(null, langue);

            // 是否支持标记
            Method getSign = aClass.getDeclaredMethod("getSign", String.class);
            int sign = (int) getSign.invoke(null, langue);

            // 是否支持水印
            Method getWatermark = aClass.getDeclaredMethod("getWatermark", String.class);
            int watermark = (int) getWatermark.invoke(null, langue);

            // 描述
            Method getDescription = aClass.getDeclaredMethod("getDescription", String.class);
            String description = (String) getDescription.invoke(null, langue);
            //jar属性文件读取完毕
            myClassLoader.close();
            //通过ID查询是否有记录
            FileType fileType = fileService.getFileTypeByUuid(uuid);
            if (fileType != null && Double.compare(fileType.getVersion(), version) != -1) {
                //版本过低

                //删除文件
                boolean f = FileUtils.delete(path);

                if (f) {
                    //删除目录
                    int lastIndex = path.lastIndexOf("/");
                    path = path.substring(0, lastIndex);
                    FileUtils.delete(path);
                }
                //版本过低
                return new ResponseBody(Long.valueOf(Constants.FAILED_CODE), BundleUtils.getValue(Constants.lowVersion));
            } else {
                FileType ft = new FileType();
                ft.setUuid(uuid);
                ft.setDescription(description);
                ft.setCatalog(catalog);
                ft.setType(type);
                ft.setCatalogWatermark(catalogWatermark);
                ft.setMethodWatermark(methodWatermark);
                ft.setFileName(originalFilename);
                ft.setPath(path);
                ft.setMethod(method);
                ft.setSign(sign);
                ft.setWatermark(watermark);
                ft.setSwf(swf);
                ft.setVersion(version);
                if (fileType == null) {
                    //新纪录，直接添加
                    ft.setStatus(Constants.YES);
                    ft.setTime(new Date());
                    fileService.save(ft);
                } else {
                    //提示用户是否更新版本
                    FileType save = fileService.save(ft);
                    FileTypeVo fileTypeVo = ModelChange.changeEntity(FileTypeVo.class, save);

                    return new ResponseBody(Constants.HIGH_VERSION, BundleUtils.getValue(Constants.highVersion), fileTypeVo);
                }
            }

            return new ResponseBody(Long.valueOf(Constants.SUCCESS_CODE), BundleUtils.getValue(Constants.success));
        }
        return new ResponseBody(Long.valueOf(Constants.FAILED_CODE), BundleUtils.getValue(Constants.errMes));

    }

    /**
     * 算法版本更新
     *
     * @param id
     * @param flag true or false
     * @return
     */
    @RequestMapping(value = "/updateVersion", method = RequestMethod.GET)
    public Object updateVersion(Long id, boolean flag) {
        fileService.updateVersion(id, flag);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

    /**
     * 获取所有文件格式 分页
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllFileType", method = RequestMethod.GET)
    public Object getAllFileType(int pageNo, int pageSize) throws Exception {
        PageQuery fileTypeVos = fileService.getAllFileType(pageNo, pageSize);
        return new ResponseBody(Constants.SUCCESS_CODE, fileTypeVos);
    }

    /**
     * 批量导出算法
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/exportPSO", method = RequestMethod.GET)
    public Object exportPSO(String ids) {
        fileService.exportPSOBatch(ids);
        return new ResponseBody(Constants.SUCCESS_CODE);

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
        String remark = jsonObject.getString("remark");
        Long id = jsonObject.getLong("id");

        if (remark != null && remark.length() > 50)
            return new ResponseBody(Constants.EMPTY_ARRAY);
        fileService.updateRemark(id, remark);
        return new ResponseBody(Constants.SUCCESS_CODE);
    }

}
