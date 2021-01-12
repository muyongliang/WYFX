package com.wyfx;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Author:uir_lxz
 * Date:
 * Tool: IntelliJ IDEA
 * info:
 */
public class excel {
    private static ActiveXComponent xl = null; //Excel对象(防止打开多个)
    private static Dispatch workbooks = null;  //工作簿对象
    private Dispatch workbook = null; //具体工作簿
    private Dispatch sheets = null;// 获得sheets集合对象
    private Dispatch currentSheet = null;// 当前sheet

    /**
     * 释放资源
     */
    protected static void releaseSource() {
        if (xl != null) {
            xl.invoke("Quit", new Variant[]{});
            xl = null;
        }
        workbooks = null;
        ComThread.Release();
        System.gc();
    }

    /**
     * 制作标记文件
     *
     * @param filePath 上传的excel文件路径
     * @param imgUrl   服务器图片文件路径
     */
    public static boolean makeFile(String filePath, String imgUrl) {
        try {
            excel xl = new excel();
            xl.OpenExcel(filePath, false);
            xl.insertImage(imgUrl);
            xl.CloseExcel();
            releaseSource();
            System.out.println("制作excel成功！");
            return true;
        } catch (Exception var3) {
            System.out.println("制作失败");
            return false;
        }


    }

    /**
     * 打开excel文件
     *
     * @param filepath 文件路径名称
     * @param visible  是否显示打开
     */
    protected void OpenExcel(String filepath, boolean visible) {
        try {
            initComponents(); //清空原始变量
            ComThread.InitSTA();
            if (xl == null)
                xl = new ActiveXComponent("Excel.Application"); //Excel对象
            xl.setProperty("Visible", new Variant(visible));//设置是否显示打开excel
            if (workbooks == null)
                workbooks = xl.getProperty("Workbooks").toDispatch(); //打开具体工作簿
            workbook = Dispatch.invoke(workbooks, "Open", Dispatch.Method,
                    new Object[]{filepath,
                            new Variant(false), // 是否以只读方式打开
                            new Variant(false),
                            "1",
                            "pwd"},   //输入密码"pwd",若有密码则进行匹配，无则直接打开
                    new int[1]).toDispatch();
        } catch (Exception e) {
            System.out.println("打开word失败，请重新上传附件！");
            throw e;
        }
    }

    /**
     * 初始化
     */
    private void initComponents() {
        workbook = null;
        currentSheet = null;
        sheets = null;
    }

    /**
     * 得到当前sheet
     */
    private Dispatch getCurrentSheet() {
        currentSheet = Dispatch.get(workbook, "ActiveSheet").toDispatch();
        return currentSheet;
    }

    /**
     * *@param imagePath 服务器图片文件路径
     * 在当前插入点插入图片
     */
    protected void insertImage(String imagePath) {
        try {
            getCurrentSheet();
            Dispatch.call(Dispatch.get(currentSheet, "Shapes").toDispatch(),
                    "AddPicture", imagePath, true, false, "0", "0", "0", "0").toDispatch(); // 添加图片
        } catch (Exception e) {
            System.out.println("插入图片失败，检查服务器是否开启或imgUrl是否配置正确！");
            throw e;
        }

    }

    /**
     * 关闭并保存excel文档
     */
    protected void CloseExcel() {
        try {
            Dispatch.call(workbook, "Save");
            Dispatch.call(workbook, "Close", new Variant(true));
            workbook = null;
        } catch (Exception e) {
            System.out.println("保存失败，请确认用户操作excel权限正常，再重新上传新附件！");
            throw e;
        }
    }
}