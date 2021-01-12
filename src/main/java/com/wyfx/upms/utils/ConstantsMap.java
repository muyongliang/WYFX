package com.wyfx.upms.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: liuxingyu
 * DATE: 2017-06-27.12:01
 * description:
 * version:
 */
public class ConstantsMap {
    public static Map<Integer, String> langueMap = new HashMap();

    static {
        langueMap.put(0, "Login_en_US.properties");
        langueMap.put(1, "Login_zh_CN.properties");
    }
}
