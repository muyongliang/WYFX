package com.wyfx.ft.utils;

import java.io.*;
import java.util.zip.ZipFile;

/**
 * [压缩/解压缩]文件和文件夹
 */
public class ZipCompress {


    public static String readZip(String filePath) {
        String comment = null;
        FileInputStream in = null;
        try {
            File file = new File(filePath);
            int fileLen = (int) file.length();
            in = new FileInputStream(file);
            byte[] buffer = new byte[Math.min(fileLen, 65792)];
            int len;
            in.skip(fileLen - buffer.length);
            if ((len = in.read(buffer)) > 0) {
                comment = getZipCommentFromBuffer(buffer, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return comment;
    }

    private static String getZipCommentFromBuffer(byte[] buffer, int len) {
        byte[] databyte = "dddzcfra".getBytes();
        int i = findcomment(buffer, len, databyte);
        if (i == -1) return null;
        else {
            String comment = null;
            byte[] databyteend = "addzcfrb".getBytes();
            int j = findcomment(buffer, len, databyteend);
            try {
                comment = new String(buffer, i + 8, j - i - 8, "gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return comment;
        }
    }

    static int findcomment(byte[] buffer, int len, byte[] find) {
        int location = -1;
        byte[] endOfDirectoryFlag = find;
        int buffLen = Math.min(buffer.length, len);

        for (int i = buffLen - endOfDirectoryFlag.length; i >= 0; i--) {
            boolean isEndOfDirectoryFlag = true;
            for (int k = 0; k < endOfDirectoryFlag.length; k++) {
                if (buffer[i + k] != endOfDirectoryFlag[k]) {
                    isEndOfDirectoryFlag = false;
                    break;
                }
            }
            if (isEndOfDirectoryFlag) {
                location = i;
                break;
            }
        }
        return location;
    }

    public static void writeZip(String filename, String comment) {
        ZipFile zipFile = null;
        ByteArrayOutputStream outputStream = null;
        RandomAccessFile accessFile = null;
        File file = new File(filename);
        try {
            byte[] byteComment = ("dddzcfra" + comment + "addzcfrb").getBytes("gbk");
            accessFile = new RandomAccessFile(file, "rw");

            long fileLen = file.length();
            int bufferlen = (fileLen > 65792) ? 65792 : (int) fileLen;
            byte[] buffer = new byte[bufferlen];
            int len, commentlen = 0;
            long commentposition = 0;

            accessFile.seek(fileLen - buffer.length);
            if ((len = accessFile.read(buffer)) > 0) {
                byte[] databyte = {0x50, 0x4b, 0x05, 0x06};
                commentposition = fileLen - buffer.length + findcomment(buffer, len, databyte);
                commentlen = (short) buffer[(int) commentposition + 20];
            }
            if (commentposition > 0) {
                accessFile.seek(commentposition + 22 + commentlen);
                accessFile.write(byteComment);
                long shengyu = accessFile.length() - accessFile.getFilePointer();

                byte[] kong = new byte[(int) shengyu];
                accessFile.write(kong);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (accessFile != null) {
                    accessFile.close();
                }
            } catch (Exception e) {

            }

        }
    }
}