package com.wyfx.ft.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.xmlbeans.impl.util.HexBin.bytesToString;

/**
 * Created by liu on 2017/12/11.
 */
public class MD5Utils {
    /**
     * 2      * 计算文件的MD5码
     * 3      * @param file
     * 4      * @return
     * 5
     */
    public static String getMD5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length = -1;
            System.out.println("开始算");
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            System.out.println("算完了");
            return bytesToString(md.digest());
        } catch (IOException ex) {
            Logger.getLogger(MD5Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(MD5Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        String md5 = getMD5(new File("E:/aaa.ppt"));
        System.err.println(md5);//5F3BB97CBE281CDCB6E711566A1A74EE
        //C57B3BAE8ECDD3745FB5479910C076E7
    }
}
