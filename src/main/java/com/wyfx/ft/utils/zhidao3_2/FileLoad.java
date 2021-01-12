package com.wyfx.ft.utils.zhidao3_2;

import java.io.File;
import java.io.FileInputStream;

public class FileLoad {
    public static byte[] getContent(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("输入有误，该文件不存在");
        }
        FileInputStream fis = new FileInputStream(file);
        int length = (int) file.length();
        byte[] data = new byte[length];
        fis.read(data);
        fis.close();
        return data;
    }
}
