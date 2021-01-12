package com.wyfx.ft.utils;

import is.tagomor.woothee.Classifier;

import java.util.Map;

/**
 * Created by www on 2016/9/1.
 */
public class UaUtils {

    public static UaClass parseUa(String userAgent) {
        String browser = "";
        String browserVersion = "";
        String system = "";
        String systemVersion = "";
        String device = "";

        Map r = Classifier.parse(userAgent);

        try {
            browser = r.get("name").toString();
        } catch (Exception e) {
        }

        try {
            browserVersion = r.get("version").toString();
        } catch (Exception e) {
        }

        try {
            system = r.get("os").toString();
        } catch (Exception e) {
        }

        try {
            systemVersion = r.get("os_version").toString();
        } catch (Exception e) {
        }

        try {
            device = r.get("category").toString();
        } catch (Exception e) {
        }

        return new UaClass(browser, browserVersion, system, systemVersion, device);
    }

    public static class UaClass {
        public String browser;
        public String browserVersion;
        public String system;
        public String systemVersion;
        public String device;

        public UaClass(String browser, String browserVersion, String system, String systemVersion, String device) {
            this.browser = browser;
            this.browserVersion = browserVersion;
            this.system = system;
            this.systemVersion = systemVersion;
            this.device = device;
        }
    }

}
