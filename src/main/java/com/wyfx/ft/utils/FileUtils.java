package com.wyfx.ft.utils;

import com.wyfx.upms.utils.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.16:36
 * description:文件上传
 * version:
 */
public class FileUtils {
    /**
     * 保存文件
     *
     * @param request
     * @param file
     * @return
     */
    public static String saveFile(HttpServletRequest request, MultipartFile file) {

        if (file != null) {
            try {

                // 日期格式化
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                // 当前时间，获取图片名称
                String name = df.format(new Date());
                // 获取三位随机数，添加到图片名称后面
                Random r = new Random();
                for (int i = 0; i < 3; i++) {
                    name += r.nextInt(10);
                }
                String dirPath = Constants.PATH + name + "/";
                // String dirPath = request.getSession().getServletContext().getRealPath("/") + "upload\\file\\"+ name + "\\";

                String filePath = dirPath + file.getOriginalFilename();

                File dirP = new File(dirPath);
                File fileP = new File(filePath);
                if (!dirP.exists()) {
                    dirP.mkdirs();
                }
                // 转存文件
                file.transferTo(fileP);
                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;

    }

    public static String getRandomPath() {
        // 日期格式化
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // 当前时间，获取图片名称
        String name = df.format(new Date());
        // 获取三位随机数，添加到图片名称后面
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            name += r.nextInt(10);
        }
        String dirPath = Constants.PATH + name + "/";
        File file = new File(dirPath);
        if (!file.exists())
            file.mkdirs();
        return dirPath;
    }

    /**
     * 算法导入
     *
     * @param file
     * @return
     */
    public static boolean saveJarFile(MultipartFile file, HttpServletRequest request) {

        if (file != null) {
            try {

                String dirPath = request.getSession().getServletContext().getRealPath("/") + "WEB-INF\\lib";

                String filePath = dirPath + file.getOriginalFilename();

                File dirP = new File(dirPath);
                File fileP = new File(filePath);
                if (!dirP.exists()) {
                    dirP.mkdirs();
                }
                // 转存文件
                file.transferTo(fileP);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;

    }


    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtils.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtils.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除文件和文件夹
     *
     * @param path 文件全路径
     */
    public static void deleteFileAndDirectory(String path) {
        //删除文件
        boolean f = FileUtils.delete(path);

        if (f) {
            //删除目录
            int lastIndex = path.lastIndexOf("/");
            path = path.substring(0, lastIndex);
            FileUtils.delete(path);
        }
    }
}
