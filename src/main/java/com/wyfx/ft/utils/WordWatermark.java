package com.wyfx.ft.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * Created by lxz on 2017/8/07.
 */

public class WordWatermark {
    static final int w = 4;


    /**
     * 提取水印
     *
     * @param in 输入文件
     * @return
     */
    public static String extract(File in) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(in);
            XWPFDocument document = new XWPFDocument(fileInputStream);
            String extractedMark = null;
            List<XWPFPictureData> allPictures = document.getAllPictures();
            for (XWPFPictureData pictureData : document.getAllPictures()) {

                //png file

                if (pictureData.getPictureType() == 6) {
                    InputStream inputStream = pictureData.getPackagePart().getInputStream();
                    //跳过不必要的大文件
                    if (inputStream.available() > 400) continue;

                    BufferedImage image = ImageIO.read(inputStream);

                    if (image.getWidth() != w || image.getWidth() != w) continue;

                    extractedMark = extractImageMark(image);
                    System.out.println(extractedMark);
                    inputStream.close();
                }
            }

            fileInputStream.close();
//            String mark = extractedMark+"word";

            return extractedMark;

            //    System.out.println(waterbits);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    protected static String extractImageMark(BufferedImage image) {
        char[] waterbits = new char[32];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < w; j++) {
                int val = image.getRGB(i, j);
                waterbits[((i * w * 2 + j * 2) % 32)] = (char) (val >> 24);
                waterbits[((i * w * 2 + j * 2) % 32) + 1] = (char) (val & 0xff);
            }
        }
        return new String(waterbits);
    }

}


