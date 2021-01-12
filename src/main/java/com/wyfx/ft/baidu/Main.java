package com.wyfx.ft.baidu;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20171204000102094";
    private static final String SECURITY_KEY = "Txb6NGHTHYEizSw6YCOw";

    public static String translate(String text) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = text;
//        String query = "Indonesia";
        String result = api.getTransResult(query, "auto", "zh");
        JSONObject jsonObject = JSONObject.parseObject(result);
        String trans_result = jsonObject.getString("trans_result");
        JSONArray jsonArray = JSONArray.parseArray(trans_result);
        JSONObject o = (JSONObject) jsonArray.get(0);
        String dst = o.getString("dst");
        System.out.println(dst);
        return dst;
    }

}
