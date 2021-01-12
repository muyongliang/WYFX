package com.wyfx.ft.utils.zhidao3_2;

import java.io.File;
import java.io.FileOutputStream;

public class FileOutPut {
    public static void output(String text, String fileName) throws Exception {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(text.getBytes());
        fos.close();
    }
}
