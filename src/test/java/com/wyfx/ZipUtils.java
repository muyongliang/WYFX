package com.wyfx;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * Created by liu on 2017/9/28.
 */
public class ZipUtils {
    /**
     * 对文件加密压缩
     *
     * @param zipPath 压缩后文件路径
     * @param params  被压缩的文件路径
     */
    public static void encrypt(String zipPath, String password, String... params) {

        try {
            final ZipFile zipFile = new ZipFile(zipPath); // 創建zip包，指定了zip路徑和zip名稱
            final ArrayList<File> fileAddZip = new ArrayList<File>(); // 向zip包中添加文件集合
            for (int i = 0; i < params.length; i++) {
                fileAddZip.add(new File(params[i])); // 向zip包中添加一个word文件}
            }

            final ZipParameters parameters = new ZipParameters(); // 设置zip包的一些参数集合
            parameters.setEncryptFiles(true); // 是否设置密码（此处设置为：是）
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式(默认值)
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 普通级别（参数很多）
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密级别

            parameters.setPassword(password); // 压缩包密码为123456
            zipFile.createZipFile(fileAddZip, parameters); // 创建压缩包完成
        } catch (final ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param zipPath 需解压文件的路径
     * @param path    解压后文件的存放路径
     */
    public static void decode(String zipPath, String password, String path) {
        try {
            final ZipFile zipFile = new ZipFile(zipPath); // 根据路径取得需要解压的Zip文件
            if (zipFile.isEncrypted()) { // 判断文件是否加码
                zipFile.setPassword(password); // 密码为123456
            }
            zipFile.extractAll(path); // 压缩包文件解压路径
        } catch (final ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        encrypt("F:/bb.zip","202cb962ac59075b964b07152d234b70","F:/散落的花瓣视频.avi","F:/下载.jpg");
//       // decode("F:/aa.zip","123456","F:/bb");
//
//        String s = MD5Utils.md5("123456");
//        System.err.println(s);

        try {

            URL url = new URL("file:E:/excel2.0.jar");
            URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread()
                    .getContextClassLoader());
            Class<?> aClass = myClassLoader.loadClass("com.example.wyfx.ExcelWatermark");
//            Class<?> aClass = myClassLoader.loadClass("com.example.wyfx.CacheConfiguration");
            Method method = aClass.getDeclaredMethod("handleFile", File.class, File.class, String.class);

//            Method method = aClass.getDeclaredMethod("getUuid",String.class);
//            String uuid = (String) method.invoke(null,"zh_CN");
            File in = new File("E:/abc.xlsx");
            File out = new File("F:/abc.xlsx");

            boolean is_watermark = (boolean) method.invoke(aClass, in, out, "5b4593c68197457298628e9c5363b9ab");
            // boolean is_watermark = ExcelWatermark.handleFile(in,out,"5b4593c68197457298628e9c5363b9ab");
            System.err.println(is_watermark);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
