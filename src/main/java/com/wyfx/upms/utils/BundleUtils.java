package com.wyfx.upms.utils;

import java.util.*;

/**
 * Author: liuxingyu
 * DATE: 2017-07-14.10:00
 * description:读取国际化配置文件
 * version:
 */

public class BundleUtils {
    public static Map getMap(String msg) {
        String langue = WyfxContext.getLangue();
        if (langue == null) {
            langue = Constants.DEFAULT_LANGUE;
        }
        String[] strings = langue.split("_");
        Locale locale = new Locale(strings[0], strings[1]);
        ResourceBundle bundle = ResourceBundle.getBundle(msg, locale);
        Enumeration<String> keys = bundle.getKeys();
        Map<String, String> map = new HashMap<>();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = bundle.getString(key);
            map.put(key, value);
        }
        return map;
    }

    public static String getValue(String key) {
        String langue = WyfxContext.getLangue();
        if (langue == null) {
            langue = Constants.DEFAULT_LANGUE;
        }
        Locale locale = new Locale(langue);
        ResourceBundle bundle = ResourceBundle.getBundle(Constants.ERROR_MSG, locale);
        String value = bundle.getString(key);
        return value;
    }

    public static void main(String[] args) {
        Double a = 1.0;
        Double b = 2.0;
        int compare = Double.compare(b, a);


    }


}
