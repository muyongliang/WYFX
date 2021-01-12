package com.wyfx.ft.utils;

import java.io.*;
import java.util.zip.ZipInputStream;

/**
 * Created by Administrator on 2017/8/19.
 */
public class ExtractAVI {
    public static final byte CUM = 4;
    public static final byte CEM = 6;
    private static File masterFile;

    private static byte features;

    private static int i, j, inputOutputMarker, messageSize, tempInt;

    private static byte byte1;
    private static byte byte2;
    private static byte byte3;
    private static byte[] byteArrayIn;
    private static String masterExtension, message;

    public static String extract(File fp) {
        String filepath = fp.getAbsolutePath();
        String[] currentpath = filepath.split("\\.");
        String extractfile;
        if (currentpath[1].equals("avi")) {
            extractfile = extractfile(fp);
        } else {
            String changepath = currentpath[0] + ".avi";
            File changefile = new File(changepath);
            fp.renameTo(changefile);
            extractfile = extractfile(changefile);
            changefile.renameTo(fp);
        }
        return extractfile;
    }

    public static String extractfile(File fp) {
        AVIWatermark.WMInformation info = new AVIWatermark.WMInformation(fp);
        String messg = null;
        features = info.getFeatures();
        try {
            masterFile = info.getFile();
            byteArrayIn = new byte[(int) masterFile.length()];

            DataInputStream in = new DataInputStream(new FileInputStream(masterFile));
            in.read(byteArrayIn, 0, (int) masterFile.length());
            in.close();

            messageSize = info.getDataLength();

            if (messageSize <= 0) {
                message = "Unexpected size of message: 0.";
                return ("#FAILED#");
            }

            byte[] messageArray = new byte[messageSize];
            inputOutputMarker = info.getInputMarker();
            readBytes(messageArray);

            // Uncompress the message if required
            if (features == CUM || features == CEM) {
                ByteArrayOutputStream by = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(by);

                ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(messageArray));
                zipIn.getNextEntry();
                byteArrayIn = new byte[1024];
                while ((tempInt = zipIn.read(byteArrayIn, 0, 1024)) != -1)
                    out.write(byteArrayIn, 0, tempInt);

                zipIn.close();
                out.close();
                messageArray = by.toByteArray();
                messageSize = messageArray.length;
                messg = new String(byteToCharArray(messageArray));
            }

            System.out.println(messg);
        } catch (Exception e) {
            message = "Oops!!\n Error: " + e;
            e.printStackTrace();
            System.out.println("出错");
            return ("#FAILED#");
        }

        message = "Message size: " + messageSize + " B";
        return messg;
    }

    private static void readBytes(byte[] bytes) {
        int size = bytes.length;
        try {
            for (int i = 0; i < size; i++) {
                bytes[i] = byteArrayIn[inputOutputMarker];
                inputOutputMarker++;
            }
        } catch (Exception e) {
            return;
        }
        return;
    }

    static char[] byteToCharArray(byte[] bytes) {
        int size = bytes.length, i;
        char[] chars = new char[size];
        for (i = 0; i < size; i++) {
            bytes[i] &= 0x7F;
            chars[i] = (char) bytes[i];
        }
        return chars;
    }
}
