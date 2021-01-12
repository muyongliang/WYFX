package com.wyfx.ft.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by test46 on 2016/4/19.
 */
/*支持的格式:AVI、3GP、MP4、MKV、WMV、RMVB（视频有少许损坏）、MPG*/
public class AVIWatermark {

    public static final String VERSION = "1.0.0";
    public static final byte[] VERSION_BYTE = {'1', '0', '0'};
    public static final int OFFSET_JPG = 3;
    public static final int OFFSET_PNG = 42;
    public static final int OFFSET_MP3 = 26;
    public static final int OFFSET_MPG = 32;
    public static final int OFFSET_WAV = 45;
    public static final int OFFSET_DAT = 16;
    public static final int OFFSET_AVI = 56;
    public static final int OFFSET_GIF_BMP_TIF = 32;
    public static final short HEADER_LENGTH = 15 * 4;
    //  Three letters indicate:
    //  Uncompressed/Compressed  Encrypted/Unencrypted  Message/File
    public static final byte UUM = 0;
    public static final byte UUF = 1;
    public static final byte UEM = 2;
    public static final byte UEF = 3;
    public static final byte CUM = 4;
    public static final byte CUF = 5;
    public static final byte CEM = 6;
    public static final byte CEF = 7;

    private static Cipher cipher;

    private static SecretKeySpec spec;
    private static String masterExtension, message;

    private static File masterFile;
    // This byte stores the features being used by the file
    private static byte features;
    private static int inputFileSize;
    private static int i, j, inputOutputMarker, messageSize, tempInt;
    private static short compressionRatio = 0, temp;
    private static byte byte1;
    private static byte byte2;
    private static byte byte3;
    private static byte[] byteArrayIn;
    private static ByteArrayOutputStream byteOut;


    private static void embedBytes(byte[] bytes) {
        int size = bytes.length;

        for (int i = 0; i < size; i++) {
            byte1 = bytes[i];
            for (int j = 6; j >= 0; j -= 2) {
                byte2 = byte1;
                byte2 >>= j;
                byte2 &= 0x03;

                byte3 = byteArrayIn[inputOutputMarker];
                byte3 &= 0xFC;
                byte3 |= byte2;
                byteOut.write(byte3);
                inputOutputMarker++;
            }
        }
    }

    // Method used to write bytes into the output array
    private static void writeBytes(byte[] bytes) {
        int size = bytes.length;

        for (int i = 0; i < size; i++) {
            byteOut.write(bytes[i]);
            inputOutputMarker++;
        }
    }

    public static boolean handleFile(File fp, File ofp, String watermark) {

        return embedMessage(fp, ofp, watermark);
    }

    public static boolean embedMessage(File masterFile, File outputFile, String watermark) {


        messageSize = watermark.length();

        int compression = 5;
        features = CEM;

        try {
            byteOut = new ByteArrayOutputStream();
            // Convert message into a character array
            byte[] messageArray = watermark.getBytes();
            messageSize = messageArray.length;
            inputFileSize = (int) masterFile.length();

            // create a byte array of length equal to size of input file
            byteArrayIn = new byte[inputFileSize];

            // Open the input file read all bytes into byteArrayIn
            DataInputStream in = new DataInputStream(new FileInputStream(masterFile));
            in.read(byteArrayIn, 0, inputFileSize);
            in.close();


            byteOut.write(byteArrayIn, 0, OFFSET_AVI);
            inputOutputMarker = OFFSET_AVI;


            // Convert the 32 bit input file size into byte array
            byte[] tempByte = new byte[4];
            for (i = 24, j = 0; i >= 0; i -= 8, j++) {
                tempInt = inputFileSize;
                tempInt >>= i;
                tempInt &= 0x000000FF;
                tempByte[j] = (byte) tempInt;
            }
            // Embed 4 byte input File size array into the master file
            embedBytes(tempByte);

            // Write the remaining bytes
            byteOut.write(byteArrayIn, inputOutputMarker, inputFileSize - inputOutputMarker);
            inputOutputMarker = inputFileSize;

            // Embed the 3 byte version information into the file
            writeBytes(VERSION_BYTE);

            // Write 1 byte for features
            writeBytes(new byte[]{features});

            // Compress the message if required
            if (features == CUM || features == CEM) {
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                ZipOutputStream zOut = new ZipOutputStream(arrayOutputStream);
                ZipEntry entry = new ZipEntry("MESSAGE");
                zOut.setLevel(compression);
                zOut.putNextEntry(entry);

                zOut.write(messageArray, 0, messageSize);
                zOut.closeEntry();
                zOut.finish();
                zOut.close();

                // Get the compressed message byte array
                messageArray = arrayOutputStream.toByteArray();
                compressionRatio = (short) ((double) messageArray.length / (double) messageSize * 100.0);
                messageSize = messageArray.length;
            }

            // Embed 1 byte compression ratio into the output file
            writeBytes(new byte[]{(byte) compressionRatio});

            // Convery the 32 bit message size into byte array
            tempByte = new byte[4];
            for (i = 24, j = 0; i >= 0; i -= 8, j++) {
                tempInt = messageSize;
                tempInt >>= i;
                tempInt &= 0x000000FF;
                tempByte[j] = (byte) tempInt;
            }
            // Embed 4 byte messageSize array into the master file
            writeBytes(tempByte);

            // Embed the message
            writeBytes(messageArray);

            DataOutputStream out = new DataOutputStream(new FileOutputStream(outputFile));
            //out.write(byteArrayOut, 0, byteArrayOut.length);
            byteOut.writeTo(out);
            out.close();
        } catch (EOFException e) {
        } catch (Exception e) {
            message = "Oops!!\nError: " + e.toString();
            e.printStackTrace();
            return false;
        }

        message = "Message embedded successfully in file '" + outputFile.getName() + "'.";
        return true;
    }

    static class WMInformation {
        private final File file;
        private File dataFile = null;
        private String starter;
        private String version;
        private byte features;
        private short compressionRatio;
        private int dataLength, temp;
        private boolean isEster = false;

        private byte[] byteArray;
        private byte[] name;
        private byte byte1;
        private byte byte2;
        private int inputMarker, i, j;

        public WMInformation(File file) {
            this.file = file;
            isEster = false;

            if (!file.exists()) {
                starter = null;
                return;
            }

            if (file.getName().equals("Sec#x&y")) {
                isEster = true;
                return;
            }

            byteArray = new byte[(int) file.length()];
            try {
                DataInputStream in = new DataInputStream(new FileInputStream(file));
                in.read(byteArray, 0, (int) file.length());
                in.close();
            } catch (Exception e) {
                starter = null;
                return;
            }

            // Obtain the original file length
            name = new byte[4];

            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.length() - 3);

            if (fileExtension.equalsIgnoreCase("jpg"))
                inputMarker = OFFSET_JPG;
            else if (fileExtension.equalsIgnoreCase("png"))
                inputMarker = OFFSET_PNG;
            else if (fileExtension.equalsIgnoreCase("mp3"))
                inputMarker = OFFSET_MP3;
            else if (fileExtension.equalsIgnoreCase("avi"))
                inputMarker = OFFSET_AVI;
            else
                inputMarker = OFFSET_GIF_BMP_TIF;

            retrieveBytes(name, byteArray, inputMarker);
            dataLength = 0;
            for (i = 24, j = 0; i >= 0; i -= 8, j++) {
                temp = name[j];
                temp &= 0x000000FF;
                temp <<= i;
                dataLength |= temp;
            }
            inputMarker = dataLength;

            if (dataLength < 0 || dataLength > file.length()) {
                starter = "Invalid";
                return;
            } else
                starter = "Admin";

            // Retrive the name and version information
            byte[] versionArray = new byte[3];
            readBytes(versionArray, byteArray, inputMarker);
            char[] versionTemp = byteToCharArray(versionArray);
            char[] ver = new char[5];
            for (i = 0, j = 0; i < 5; i++)
                if (i == 1 || i == 3) ver[i] = '.';
                else {
                    ver[i] = versionTemp[j++];
                }

            version = new String(ver);

            // Obtain the features
            name = new byte[1];
            readBytes(name);
            features = name[0];

            // Obtain the compression ratio
            readBytes(name);
            name[0] &= 0x7F;
            compressionRatio = name[0];

            // Obtain the data length
            name = new byte[4];
            readBytes(name);
            dataLength = 0;
            for (i = 24, j = 0; i >= 0; i -= 8, j++) {
                temp = name[j];
                temp &= 0x000000FF;
                temp <<= i;
                dataLength |= temp;
            }
        }

        // Accessor methods
        public File getFile() {
            return file;
        }

        public int getInputMarker() {
            return inputMarker;
        }

        public File getDataFile() {
            return dataFile;
        }

        // Mutator methods
        public void setDataFile(File dataFile) {
            this.dataFile = dataFile;
        }

        public String getVersion() {
            return version;
        }

        public byte getFeatures() {
            return features;
        }

        public short getCompressionRatio() {
            return compressionRatio;
        }

        public int getDataLength() {
            return dataLength;
        }

        public boolean isEster() {
            return isEster;
        }

        private void retrieveBytes(byte[] bytes, byte[] array, int marker) {
            byteArray = array;
            inputMarker = marker;

            int size = bytes.length;

            for (i = 0; i < size; i++) {
                byte1 = 0;
                for (j = 6; j >= 0; j -= 2) {
                    byte2 = byteArray[inputMarker];
                    inputMarker++;

                    byte2 &= 0x03;
                    byte2 <<= j;
                    byte1 |= byte2;
                }
                bytes[i] = byte1;
            }
        }

        private void retrieveBytes(byte[] bytes) {
            int size = bytes.length;

            for (i = 0; i < size; i++) {
                byte1 = 0;
                for (j = 6; j >= 0; j -= 2) {
                    byte2 = byteArray[inputMarker];
                    inputMarker++;

                    byte2 &= 0x03;
                    byte2 <<= j;
                    byte1 |= byte2;
                }
                bytes[i] = byte1;
            }
        }

        private void readBytes(byte[] bytes, byte[] array, int marker) {
            byteArray = array;
            inputMarker = marker;

            int size = bytes.length;

            for (i = 0; i < size; i++) {
                bytes[i] = byteArray[inputMarker];
                inputMarker++;
            }
        }

        private void readBytes(byte[] bytes) {
            int size = bytes.length;

            for (i = 0; i < size; i++) {
                bytes[i] = byteArray[inputMarker];
                inputMarker++;
            }
        }

        public char[] byteToCharArray(byte[] bytes) {
            int size = bytes.length, i;
            char[] chars = new char[size];
            for (i = 0; i < size; i++) {
                bytes[i] &= 0x7F;
                chars[i] = (char) bytes[i];
            }
            return chars;
        }

        public boolean isValid() {
            return starter.equals("Admin");
        }
    }
}

