package com.wyfx.ft.utils;

import org.apache.poi.POIDocument;
import org.apache.poi.hpsf.CustomProperties;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/21.
 */
public class ExtractDoc {
    public static String extractDoc(File in) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(in);

            HWPFDocument document = new HWPFDocument(fileInputStream);

            String extractedMark = propertiesExtract(document);

            fileInputStream.close();
            System.out.println(extractedMark);
            return extractedMark;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
