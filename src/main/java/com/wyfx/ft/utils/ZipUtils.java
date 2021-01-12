package com.wyfx.ft.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by liu on 2017/7/25.
 * 压缩文件工具类
 */
public class ZipUtils {
    public static void doCompress(String srcFile, String zipFile) throws Exception {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 文件压缩
     *
     * @param srcFile  目录或者单个文件
     * @param destFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File destFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destFile));
        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            for (File file : files) {
                doCompress(file, out);
            }
        } else {
            doCompress(srcFile, out);
        }
    }

    public static void doCompress(String pathname, ZipOutputStream out) throws IOException {
        doCompress(new File(pathname), out);
    }

    public static void doCompress(File file, ZipOutputStream out) throws IOException {
        if (file.exists()) {
            int num = 0;
            byte[] buffer = new byte[1024];

            String name = file.getName();
            FileInputStream fis = new FileInputStream(file);
            put(num, out, file);
            int len = 0;
            // 读取文件的内容,打包到zip文件
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.closeEntry();
            fis.close();
        }
    }

    public static void put(int num, ZipOutputStream out, File file) {
        String name = file.getName();
        try {
            if (num == 0) {

                out.putNextEntry(new ZipEntry(name));
            } else {
                int i = name.lastIndexOf(".");
                out.putNextEntry(new ZipEntry(name.substring(0, i) + "(" + num + ")" + name.substring(i)));
            }
        } catch (Exception e) {
            num++;
            if (num <= 10) {
                put(num, out, file);
            }

        }
    }

    public static void main(String[] args) {
        int[] s = new int[]{3, 5, 234, 231, 21, 4, 4};
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s.length - i; j++) {

            }
        }

    }
}
