package com.wyfx.sf.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
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
    public static void decode(String zipPath, String password, String path) throws ZipException {
        File file = new File(path);
        file.delete();
        if (!file.exists())
            file.mkdir();
        //try {
        final ZipFile zipFile = new ZipFile(zipPath); // 根据路径取得需要解压的Zip文件
        if (zipFile.isEncrypted()) { // 判断文件是否加码
            zipFile.setPassword(password); // 密码为123456
        } else {
            throw new ZipException();
        }
        zipFile.extractAll(path); // 压缩包文件解压路径
//        } catch (final ZipException e) {
//            // TODO Auto-generated catch block
//            throw e;
//        }
    }

    public static void main(String[] args) throws ZipException {
        //encrypt("F:/sf.zip","e10adc3949ba59abbe56e057f20f883e","F:/apk.properties","F:/sf.apk");
        decode("F:/sf.zip", "e10adc3949ba59abbe56e057f20f883e", "F:/aaa");

    }
}
