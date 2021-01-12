package com.wyfx.ft.utils;

import org.apache.poi.hslf.usermodel.HSLFPictureData;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by lxz on 2017/8/07.
 */
public class PPTWatermark {
    static final int w = 4;

    /**
     * 读取文件中的指定图片
     *
     * @param in 输入文件
     * @return
     */
    public static String extractPptx(File in) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(in);

            XMLSlideShow ppt = new XMLSlideShow(fileInputStream);
            String extractedMark = null;

            for (XSLFPictureData pictureData : ppt.getPictureData()) {
                //png file
                if (pictureData.getType() == PictureData.PictureType.PNG) {

                    InputStream inputStream = pictureData.getInputStream();
                    //跳过不必要的大文件
                    if (inputStream.available() > 200) continue;

                    BufferedImage image = ImageIO.read(inputStream);
                    if (image.getWidth() != w || image.getWidth() != w) continue;

                    extractedMark = extractImageMark(image);
                    System.out.println(extractedMark);
                    inputStream.close();
                    break;
                }
            }

            fileInputStream.close();
            ppt.close();
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

    public static String extractPpt(File in) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(in);

            HSLFSlideShow ppt = new HSLFSlideShow(fileInputStream);
            String extractedMark = null;

            for (HSLFPictureData pictureData : ppt.getPictureData()) {
                //png file
                if (pictureData.getType() == PictureData.PictureType.PNG) {

                    byte[] pictureByte = pictureData.getData();
                    if (pictureByte.length > 200) continue;

                    InputStream inputStream = new ByteArrayInputStream(pictureByte);

                    BufferedImage image = ImageIO.read(inputStream);
                    if (image.getWidth() != w || image.getWidth() != w) continue;

                    extractedMark = extractImageMark(image);
                    System.out.println(extractedMark);
                    inputStream.close();
                    break;
                }
            }

            fileInputStream.close();
            ppt.close();
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

    /**
     * 提取图片中的水印信息
     *
     * @param image 输入文件
     * @return 水印
     */
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
