package com.wyfx.ft.utils;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;

/**
 * Created by Administrator on 2017/8/19.
 */
public class ExtractPdf {
    /**
     * 检测水印
     *
     * @param file 输入文件
     * @return outstring水印信息
     */
    public static String extract(String file) {
        try {
            PdfReader reader = new PdfReader(file);
            Rectangle rect = new Rectangle(0, 0, 0, 0);
            RenderFilter filter = new RegionTextRenderFilter(rect);
            RenderFilter[] a = {filter};
            FilteredTextRenderListener strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), a);
            String outstring = PdfTextExtractor.getTextFromPage(reader, 1, strategy);
            int indexbegin = outstring.indexOf(">>>");
            int indexend = outstring.indexOf("<<<");
            if (indexbegin == -1 || indexend == -1) return "";
            else if (indexend > indexbegin) {
                outstring = outstring.substring(indexbegin + 3, indexend);
                System.out.println(outstring);
                return outstring;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
