package com.wyfx.ft.utils;

import org.apache.poi.POIDocument;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * Created by lxz on 2017/8/07.
 */

public class ExcelWatermark {
    static final int w = 4;

    /**
     * 提取水印
     * @param in 输入文件
     * @return
     */

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

            Workbook wb;
            if (in.getPath().endsWith("xlsx")) {
                wb = new XSSFWorkbook(fileInputStream);
            } else {
                wb = new HSSFWorkbook(fileInputStream);
            }

            String extractedMark = null;
            for (PictureData pictureData : wb.getAllPictures()) {
                //png file
                if (pictureData.getPictureType() == Workbook.PICTURE_TYPE_PNG) {
                    InputStream inputStream = null;
                    if (pictureData instanceof XSSFPictureData) {
                        inputStream = ((XSSFPictureData) pictureData).getPackagePart().getInputStream();
                    } else {
                        byte[] picBytes = pictureData.getData();
                        if (picBytes.length > 200) continue;
                        inputStream = new ByteArrayInputStream(picBytes);
                    }

                    if (inputStream == null) continue;
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

            if (wb instanceof POIXMLDocument) {
                propertiesExtract((POIXMLDocument) wb);
            } else if (wb instanceof POIDocument) {
                propertiesExtract((POIDocument) wb);
            }

            fileInputStream.close();
            wb.close();
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

    protected static String propertiesExtract(POIXMLDocument document) {
        CTProperty mark = document.getProperties().getCustomProperties().getProperty("mark");
        if (mark != null) {
            if (mark.isSetLpwstr()) {
                return mark.getLpwstr();
            }
        }
        return null;
    }

    protected static String propertiesExtract(POIDocument document) {
        DocumentSummaryInformation documentSummaryInformation = document.getDocumentSummaryInformation();

        CustomProperties customProperties = documentSummaryInformation.getCustomProperties();

        if (customProperties == null) return null;

        Object mark = customProperties.get("mark");

        if (mark instanceof String) {
            return (String) mark;
        }

        return null;
    }


}

