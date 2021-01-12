package com.wyfx.ft.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wyfx.ft.model.FileInfo;
import com.wyfx.ft.model.FileType;
import com.wyfx.ft.model.Type;
import com.wyfx.ft.model.vo.FileInfoVo;
import com.wyfx.ft.model.vo.FileTypeVo;
import com.wyfx.ft.repository.FileRepo;
import com.wyfx.ft.repository.FileTypeRepo;
import com.wyfx.ft.repository.TypeRepo;
import com.wyfx.ft.service.FileService;
import com.wyfx.ft.utils.*;
import com.wyfx.upms.controller.PageController;
import com.wyfx.upms.utils.Constants;
import com.wyfx.upms.utils.ModelChange;
import com.wyfx.upms.utils.PageQuery;
import com.wyfx.upms.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.17:11
 * description:
 * version:
 */
@Service
@Transactional
public class FileSrvImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private FileTypeRepo fileTypeRepo;
    @Autowired
    private TypeRepo typeRepo;

    public static void main(String[] args) throws Exception {
        UUID uuid = UUID.randomUUID();
        int length = uuid.toString().replace("-", "").length();
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress(); //获取本机ip
        String hostName = addr.getHostName(); //获取本机计算机名称
        System.out.println(ip);
        System.out.println(hostName);
        Class cls = Class.forName("com.wyfx.ft.utils.word");
        Method staticMethod = cls.getDeclaredMethod("stringToDate", String.class);
        Object chb = staticMethod.invoke(cls, "2016-12-12 00:00:00");

    }

    @Override
    public FileInfo save(FileInfo fileInfo) {
        //设置文件属性 未标记
        fileInfo.setStatus(Constants.NO_FILE_SIGN);

        fileInfo.setUuid(UUID.randomUUID().toString().replace("-", ""));
        FileInfo info = fileRepo.save(fileInfo);  //存数据库操作
        return info;
    }

    @Override
    public List<Type> getFileType() throws Exception {

        List<Type> types = typeRepo.findAll();
        return types;
    }

    @Override
    public PageQuery getAllFileSign(int pageNo, int pageSize) throws Exception {
        //设置查询条件 ，已标记的文件 分页
        Pageable page = new PageRequest(pageNo - 1, pageSize);
        Page<FileInfo> fileInfos = fileRepo.findByStatus(Constants.YES_FILE_SIGN, page);
        PageQuery pageQuery = PageUtils.pagingList(FileTypeVo.class, fileInfos, pageNo, pageSize);
        return pageQuery;
    }

    @Override
    public PageQuery getAllFileSignByCondition(Long fileTypeId, Date startTime, Date endTime, int pageNo, int pageSize) throws Exception {
        //设置文件格式
        final Type type;
        if (fileTypeId != null) {
            type = new Type();
            type.setId(fileTypeId);
        } else {
            type = null;
        }
        //排序
        Sort s = new Sort(Sort.Direction.DESC, "Id");
        //分页对象
        Pageable page = new PageRequest(pageNo - 1, pageSize, s);
        //按照条件查询
        Page<FileInfo> fileInfos = fileRepo.findAll(new Specification<FileInfo>() {
            @Override
            public Predicate toPredicate(Root<FileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //设置查询条件
                if (type != null) {
                    list.add(cb.equal(root.get("type"), type));
                }
                list.add(cb.equal(root.get("status"), Constants.YES_FILE_SIGN));

                if (startTime != null) {
                    list.add(cb.between(root.get("longTime"), startTime.getTime(), endTime.getTime()));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, page);//按照条件查询

        PageQuery pageQuery = PageUtils.pagingList(FileInfoVo.class, fileInfos, pageNo, pageSize);

        return pageQuery;
    }

    @Override
    public PageQuery getAllFileSignOrWatermarkByCondition(Long fileTypeId, Date startTime, Date endTime, int pageNo, int pageSize) throws Exception {
        //设置文件格式
        final Type type;
        if (fileTypeId != null) {
            type = new Type();
            type.setId(fileTypeId);
        } else {
            type = null;
        }
        //排序
        Sort s = new Sort(Sort.Direction.DESC, "Id");
        //分页对象
        Pageable page = new PageRequest(pageNo - 1, pageSize, s);
        //按照条件查询 fireWallNumber
        Page<FileInfo> fileInfos = fileRepo.findAll(new Specification<FileInfo>() {
            @Override
            public Predicate toPredicate(Root<FileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //设置查询条件
                if (type != null) {
                    list.add(cb.equal(root.get("type"), type));
                }
                if (startTime != null) {
                    list.add(cb.between(root.get("longTime"), startTime.getTime(), endTime.getTime()));
                }
                list.add(cb.equal(root.get("status"), Constants.YES_FILE_SIGN));

                Predicate[] p = new Predicate[list.size()];

                List<Predicate> list2 = new ArrayList<>();
                //设置查询条件
                if (type != null) {
                    list2.add(cb.equal(root.get("type"), type));
                }
                if (startTime != null) {
                    list2.add(cb.between(root.get("longTime"), startTime.getTime(), endTime.getTime()));
                }
                list2.add(cb.equal(root.get("fireWallNumber"), true));

                Predicate[] p2 = new Predicate[list2.size()];
                Predicate predicate = cb.and(list.toArray(p));
                Predicate predicate2 = cb.and(list2.toArray(p2));
                Predicate[] predicates = new Predicate[]{predicate, predicate2};
                return cb.or(predicates);
            }
        }, page);//按照条件查询

        PageQuery pageQuery = PageUtils.pagingList(FileInfoVo.class, fileInfos, pageNo, pageSize);

        return pageQuery;
    }

    @Override
    public void saveUuidAndPath(String uuid, FileInfo fileInfo, String city) {
        FileInfo byUuid = fileRepo.findByUuid(uuid);

        FileInfo save;
        if (byUuid != null) {
            String path = byUuid.getPath();
            FileUtils.deleteFileAndDirectory(path);
            byUuid.setPath(fileInfo.getPath());
            byUuid.setFileName(fileInfo.getFileName());
            save = fileRepo.save(byUuid);
        } else {
            fileInfo.setUuid(uuid);
            String md5 = MD5Utils.getMD5(new File(fileInfo.getPath()));
            fileInfo.setMd5Id(md5);
            save = fileRepo.save(fileInfo);
        }
        saveFileInfo(true, save, false, "", city, "");

    }

    @Override
    public void delFile(Long fileId) {

        FileInfo fileInfo = fileRepo.findById(fileId);
        String path = fileInfo.getPath();
        //删除文件
        boolean flag = FileUtils.delete(path);
        if (flag) {
            //删除目录
            int i = path.lastIndexOf("/");
            path = path.substring(0, i);
            FileUtils.delete(path);
        }

        // 删除数据库记录
        fileRepo.delete(fileInfo);
    }

    @Override
    public FileInfo getFileById(Long fileId) {
        FileInfo fileInfo = fileRepo.findById(fileId);
        return fileInfo;
    }

    @Override
    public void signFile(String fileIds, String tag, String remark, String params, String city, String text) throws Exception {
        JSONArray jsonArray = JSONArray.parseArray(fileIds);
        JSONArray paramsArray = JSONArray.parseArray(params);
        if (jsonArray != null && jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {

                Long fileId = jsonArray.getLong(i);
                FileInfo fileInfo = fileRepo.findById(fileId);
                //1.判读文件内容是否为空
                File file = new File(fileInfo.getPath());
                if (file.exists() && file.length() == 0) {
                    fileInfo.setStatus(Constants.DANGER_FILE_EMPTY);
                    fileRepo.save(fileInfo);
                    FileUtils.deleteFileAndDirectory(fileInfo.getPath());
                    continue;
                }

                //2.判断支持的格式
                List<FileType> fileTypes = fileTypeRepo.findByStatus(Constants.YES_FILE_TYPE);
                boolean flag = false;
                //判断格式是否支持
                //记录格式的序号，执行对应类的方法
                int index = 0;
                a:
                for (FileType fileType : fileTypes) {
                    String fileName = fileInfo.getFileName();
                    int lastIndex = fileName.lastIndexOf(".");
                    String type = fileName.substring(lastIndex + 1);
                    String swf = fileType.getSwf();
                    String[] swfSplit = swf.split(",");
                    for (int j = 0; j < swfSplit.length; j++)
                        if (swfSplit[j] != null && swfSplit[j].equals(type)) {
                            fileInfo.setFileType(fileType);
                            flag = true;
                            index = j;
                            break a;
                        }

                }
                if (!flag) {
                    //格式不支持,标记格式错误
                    fileInfo.setStatus(Constants.ERROR_FILE_SIGN);
                    String path = fileInfo.getPath();
                    //删除文件和目录
                    FileUtils.deleteFileAndDirectory(path);
                    continue;
                }


                boolean is_sign = false;
                boolean is_watermark = false;
                //4.判断是否支持算法
                b:
                for (int z = 0; z < paramsArray.size(); z++) {
                    FileType fileType = fileInfo.getFileType();
                    switch (paramsArray.getInteger(z)) {
                        //追踪标记
                        case 2:
                            Integer sign = fileType.getSign();
                            if (sign == 0) {
                                //不支持追踪标记
                                fileInfo.setStatus(Constants.NOT_SUPPORT_SIGN);
                                fileRepo.save(fileInfo);
                                flag = false;
                                break b;
                            } else {
                                is_sign = true;
                            }
                            break;
                        case 1:
                            Integer watermark = fileType.getWatermark();
                            if (watermark == 0) {
                                //不支持水印标记
                                fileInfo.setStatus(Constants.NOT_SUPPORT_WATERMARK);
                                fileRepo.save(fileInfo);
                                flag = false;
                                break b;
                            } else {
                                is_watermark = true;
                            }
                            break;

                    }
                }
                //格式支持
                if (flag) {

                    URLClassLoader myClassLoader = null;
                    String filePath = fileInfo.getFileType().getPath();
                    //动态加载jar
                    URL fileUrl = new URL("file:" + filePath);
                    myClassLoader = new URLClassLoader(new URL[]{fileUrl}, Thread.currentThread()
                            .getContextClassLoader());

                    //判读是否是pdf文件
                    String name = fileInfo.getFileName();
                    if ("pdf".equals(name.substring(name.lastIndexOf(".") + 1))) {
                        makePdf(fileInfo, text, myClassLoader, is_sign, is_watermark, city, tag, remark);
                    } else {

                        makeOther(myClassLoader, fileInfo, is_sign, is_watermark, index, remark, tag, city);
                    }
                    //获取文件的md5
                    String path = fileInfo.getPath();
                    File filein = new File(path);
                    if (filein.exists()) {
                        String md5 = MD5Utils.getMD5(filein);
                        fileInfo.setMd5Id(md5);
                    }
                    fileRepo.save(fileInfo);

                }
            }
        }
    }

    private String getWaterMark(FileInfo fileInfo) {
        String path = fileInfo.getPath();

        File file = new File(path);

        int i = path.lastIndexOf(".");
        String swf = path.substring(i + 1);

        String uuid = "";
        try {
            if ("docx".equals(swf))
                uuid = WordWatermark.extract(file);
            else if ("doc".equals(swf))
                uuid = ExtractDoc.extractDoc(file);
            else if ("ppt".equals(swf))
                uuid = PPTWatermark.extractPpt(file);
            else if ("pptx".equals(swf))
                uuid = PPTWatermark.extractPptx(file);
            else if ("xls".equals(swf) || "xlsx".equals(swf))
                uuid = ExcelWatermark.extract(file);
            else if ("pdf".equals(swf))
                uuid = ExtractPdf.extract(path);
            else if ("3gp".equals(swf) || "mp4".equals(swf) || "avi".equals(swf) || "mkv".equals(swf) || "wmv".equals(swf) || "rmvb".equals(swf) || "mpg".equals(swf))
                uuid = ExtractAVI.extract(file);
            else if ("zip".equals(swf))
                uuid = ZipCompress.readZip(path);
        } catch (Exception e) {

            System.err.println("水印识别错误！！！");
        }
        return uuid;

    }

    private void makeOther(URLClassLoader myClassLoader, FileInfo fileInfo, boolean is_sign, boolean is_watermark, int index, String remark, String tag, String city) {
        try {
            //水印标记
            if (is_watermark) {

                String catalogWatermark = fileInfo.getFileType().getCatalogWatermark();
                String[] catalog = catalogWatermark.split(",");
                //如果类路径只有一个，就直接执行该方法
                if (catalog.length == 1) {
                    catalogWatermark = catalog[0];
                } else {
                    catalogWatermark = catalog[index];
                }

                String methodWatermark = fileInfo.getFileType().getMethodWatermark();
                String[] method = methodWatermark.split(",");
                //如果方法只有一个，就直接执行该方法
                if (method.length == 1) {
                    methodWatermark = method[0];
                } else {
                    methodWatermark = method[index];
                }
                //通过反射的方式获取类和方法
                Class<?> aClass1 = myClassLoader.loadClass(catalogWatermark);
                Method aClassDeclaredMethod = aClass1.getDeclaredMethod(methodWatermark, File.class, File.class, String.class);

                System.out.println(aClassDeclaredMethod.getName());
                String path = fileInfo.getPath();

                //生成一个新的路径
                int of = path.lastIndexOf("/");
                String newPath = FileUtils.getRandomPath() + path.substring(of + 1);

                //执行  定位到此位置-----xzp-1
                try {
//                    is_watermark = (boolean) aClassDeclaredMethod.invoke(aClass1, new File(path),new File(newPath),fileInfo.getUuid());
                    File in = new File(path);
                    File out = new File(newPath);
                    String uid = fileInfo.getUuid();

                    is_watermark = (boolean) aClassDeclaredMethod.invoke(null, in, out, uid);
                } catch (IllegalAccessException e) {

                    logger.error("error here");
                    e.printStackTrace();
                    throw new IllegalAccessException();
                } catch (IllegalArgumentException e) {
                    System.err.println("IllegalArgumentException");

                    e.printStackTrace();
                    throw new IllegalArgumentException();
                } catch (InvocationTargetException e) {

                    e.printStackTrace();
                    throw new Exception();
                }

                if (is_watermark) {
                    //删除原来文件和目录
                    FileUtils.deleteFileAndDirectory(path);
                    fileInfo.setPath(newPath);
                } else {
                    FileUtils.deleteFileAndDirectory(newPath);
                }
            }

            if (is_sign) {
                //生成UUID
                String uuid;
                uuid = fileInfo.getUuid();
                // fileRepo.save(fileInfo);

                //获取本机IP
                String ip = InetAddress.getLocalHost().getHostAddress();
                //拼接服务器地址
                ip = Constants.SERVER_IP;
                String url = "http://" + ip + "/wyfx/img/" + uuid + ".jpg";
                String catalog = fileInfo.getFileType().getCatalog();
                String method = fileInfo.getFileType().getMethod();

                //通过反射的方式获取类和方法
                Class<?> aClass = myClassLoader.loadClass(catalog);
                Method staticMethod = aClass.getDeclaredMethod(method, String.class, String.class);
                //执行
                is_sign = (boolean) staticMethod.invoke(aClass, fileInfo.getPath(), url);
            }
            fileInfo = saveFileInfo(is_sign, fileInfo, is_watermark, remark, city, tag);
        } catch (Exception e) {
            //标记失败
            fileInfo.setStatus(Constants.DANGER_FILE_SIGN);
            String path = fileInfo.getPath();
            //删除文件和目录
            FileUtils.deleteFileAndDirectory(path);
        } finally {

            if (myClassLoader != null) {

                try {
                    myClassLoader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void makePdf(FileInfo fileInfo, String text, URLClassLoader myClassLoader, boolean is_sign, boolean is_watermark, String city, String tag, String remark) {
//        if(text == null || "".equals(text)){
//            //未输入诱导文字
//            fileInfo.setStatus(Constants.PDF_NO_TEXT);
//            fileRepo.save(fileInfo);
//
//            //删除文件和目录
//            FileUtils.deleteFileAndDirectory(fileInfo.getPath());
//            return;
//        }
        try {
            //追踪标记
            if (is_sign) {
                //生成UUID
                String uuid;
                uuid = fileInfo.getUuid();
                // fileRepo.save(fileInfo);

                //获取本机IP
                //String ip = InetAddress.getLocalHost().getHostAddress();
                //拼接服务器地址
                String ip = Constants.SERVER_IP;
                String url = "http://" + ip + "/wyfx/img/" + uuid + ".jpg";
                String catalog = fileInfo.getFileType().getCatalog();
                String method = fileInfo.getFileType().getMethod();

                //通过反射的方式获取类和方法
                Class<?> aClass = myClassLoader.loadClass(catalog);
                Method staticMethod = aClass.getDeclaredMethod(method, String.class, String.class, String.class, String.class);
                String path = fileInfo.getPath();

                //生成一个新的路径
                int of = path.lastIndexOf("/");
                String newPath = FileUtils.getRandomPath() + path.substring(of + 1);
                //执行
                is_sign = (boolean) staticMethod.invoke(aClass, path, newPath, text, url);
                if (is_sign) {
                    //删除原来文件和目录
                    FileUtils.deleteFileAndDirectory(path);
                    fileInfo.setPath(newPath);
                } else {
                    FileUtils.deleteFileAndDirectory(newPath);
                }

            }
            //水印标记
            if (is_watermark) {

                String catalogWatermark = fileInfo.getFileType().getCatalogWatermark();


                String methodWatermark = fileInfo.getFileType().getMethodWatermark();
                //通过反射的方式获取类和方法
                Class<?> aClass1 = myClassLoader.loadClass(catalogWatermark);
                Method aClassDeclaredMethod = aClass1.getDeclaredMethod(methodWatermark, String.class, String.class, String.class);
                String path = fileInfo.getPath();

                //生成一个新的路径
                int of = path.lastIndexOf("/");
                String newPath = FileUtils.getRandomPath() + path.substring(of + 1);

                //执行
                is_watermark = (boolean) aClassDeclaredMethod.invoke(aClass1, path, newPath, fileInfo.getUuid());
                if (is_watermark) {
                    //删除原来文件和目录
                    FileUtils.deleteFileAndDirectory(path);
                    fileInfo.setPath(newPath);
                } else {
                    FileUtils.deleteFileAndDirectory(newPath);
                }
            }

            fileInfo = saveFileInfo(is_sign, fileInfo, is_watermark, remark, city, tag);
        } catch (Exception e) {
            //标记失败
            fileInfo.setStatus(Constants.DANGER_FILE_SIGN);
            String path = fileInfo.getPath();
            //删除文件和目录
            FileUtils.deleteFileAndDirectory(path);
        } finally {

            if (myClassLoader != null) {

                try {
                    myClassLoader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private FileInfo saveFileInfo(boolean is_sign, FileInfo fileInfo, boolean is_watermark, String remark, String city, String tag) {

        if (is_sign || is_watermark) {
            //设置添加时间
            fileInfo.setTime(new Date());
            //标记成功
            fileInfo.setStatus(Constants.YES_FILE_SIGN);
            fileInfo.setFireWallNumber(is_watermark);
            fileInfo.setSign(is_sign);
            fileInfo.setRemark(remark);
            fileInfo.setTag(tag);
            fileInfo.setSignCity(city);

            //标记成功，保存文件格式

            //截取文件后缀名
            String fileName = fileInfo.getFileName();
            String fix = fileName.substring(fileName.lastIndexOf(".") + 1);

            List<Type> types = typeRepo.findAll();
            if (types != null && types.size() > 0) {
                boolean isE = true;
                for (Type type : types) {
                    if (type.getType().equals(fix))
                        isE = false;
                }
                //判断格式是否存在，不存在就添加
                if (isE) {
                    Type type = new Type();
                    type.setType(fix);
                    typeRepo.save(type);
                    fileInfo.setType(type);
                } else {
                    Type type = typeRepo.findByType(fix);
                    fileInfo.setType(type);
                }
            } else {
                Type type = new Type();
                type.setType(fix);
                typeRepo.save(type);
                fileInfo.setType(type);
            }

        } else {
            //标记失败
            fileInfo.setStatus(Constants.DANGER_FILE_SIGN);
            //删除文件和目录
            FileUtils.deleteFileAndDirectory(fileInfo.getPath());
        }
        return fileInfo;
    }

    @Override
    public List<FileInfoVo> getFileByIds(String fileIds) throws Exception {
        JSONArray jsonArray = JSONArray.parseArray(fileIds);
        //将数组中的只转换为Long类型
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonArray.set(i, Long.parseLong(jsonArray.getString(i)));
        }
        List<FileInfo> fileInfos = fileRepo.findByIdIn(jsonArray);
        List<FileInfoVo> fileInfoVos = ModelChange.changeList(FileInfoVo.class, fileInfos);
        return fileInfoVos;
    }

    @Override
    public FileType getFileTypeById(Long typeId) {
        FileType fileType = fileTypeRepo.findById(typeId);
        return fileType;
    }

    @Override
    public FileType save(FileType fileType) {
        return fileTypeRepo.save(fileType);
    }

    @Override
    public PageQuery getAllFileType(int pageNo, int pageSize) throws Exception {
        Pageable page = new PageRequest(pageNo - 1, pageSize);
        Page<FileType> fileTypes = fileTypeRepo.findByStatus(Constants.YES, page);
        PageQuery pageQuery = PageUtils.pagingList(FileTypeVo.class, fileTypes, pageNo, pageSize);


        return pageQuery;
    }

    @Override
    public FileType getFileTypeByUuid(String uuid) {
        //通过uuid和状态查询
        FileType fileType = fileTypeRepo.findByUuidAndStatus(uuid, Constants.YES);
        return fileType;
    }

    @Override
    public void updateVersion(Long id, boolean flag) {
        FileType fileType = fileTypeRepo.findById(id);
        if (flag) {
            //更新版本
            String uuid = fileType.getUuid();
            //删除原来的记录
            FileType uuidAndStatus = fileTypeRepo.findByUuidAndStatus(uuid, Constants.YES);
            String path = uuidAndStatus.getPath();
            //删除文件
            boolean f = FileUtils.delete(path);

            if (f) {
                //删除目录
                int lastIndex = path.lastIndexOf("/");
                path = path.substring(0, lastIndex);
                FileUtils.delete(path);
            }
            fileTypeRepo.delete(uuidAndStatus);
            //更新新纪录状态
            fileType.setTime(new Date());
            fileType.setStatus(Constants.YES);
            fileTypeRepo.save(fileType);
        } else {
            //删除记录
            String path = fileType.getPath();

            //删除文件
            boolean f = FileUtils.delete(path);
            if (f) {
                //删除目录
                int lastIndex = path.lastIndexOf("/");
                path = path.substring(0, lastIndex);
                FileUtils.delete(path);
            }
            fileTypeRepo.delete(fileType);


        }
    }

    @Override
    public void exportPSO(Long id) {
        FileType fileType = fileTypeRepo.findById(id);
        String path = fileType.getPath();
        FileUtils.deleteFileAndDirectory(path);
        //删除和标记文件的关联
        List<FileInfo> fileInfos = fileRepo.findByFileType(fileType);
        for (FileInfo fileInfo : fileInfos) {
            fileInfo.setFileType(null);
        }
        fileRepo.save(fileInfos);
        //删除记录
        fileTypeRepo.delete(fileType);

    }

    @Override
    public void updateRemark(Long id, String remark) {
        FileType fileType = fileTypeRepo.findById(id);
        fileType.setRemark(remark);
        fileTypeRepo.save(fileType);
    }

    @Override
    public void exportPSOBatch(String ids) {
        JSONArray jsonArray = JSONArray.parseArray(ids);
        for (int i = 0; i < jsonArray.size(); i++) {
            exportPSO(jsonArray.getLong(i));
        }
    }

    @Override
    public FileInfoVo getFileNewest() throws Exception {
        List<FileInfo> fileInfos = fileRepo.findByStatus(Constants.YES);
        if (fileInfos == null || fileInfos.size() == 0) {
            return null;
        }
        FileInfoVo fileInfoVo = ModelChange.changeEntity(FileInfoVo.class, fileInfos.get(fileInfos.size() - 1));
        return fileInfoVo;
    }
}
