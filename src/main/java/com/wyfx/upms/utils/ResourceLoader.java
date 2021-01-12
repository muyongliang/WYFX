package com.wyfx.upms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liu on 2017/7/28.
 */
public class ResourceLoader {
    private static final ResourceLoader loader = new ResourceLoader();
    private static final Map<String, Properties> loaderMap = new HashMap<String, Properties>();

    private ResourceLoader() {
    }

    public static ResourceLoader getInstance() {
        return loader;
    }

    public Properties getPropFromProperties(String fileName) throws Exception {

        Properties prop = loaderMap.get(fileName);
        if (prop != null) {
            return prop;
        }
        String filePath = null;
        String configPath = System.getProperty("configurePath");

        if (configPath == null) {
            filePath = this.getClass().getClassLoader().getResource(fileName).getPath();
        } else {
            filePath = configPath + "/" + fileName;
        }
        prop = new Properties();
        prop.load(new FileInputStream(new File(filePath)));

        loaderMap.put(fileName, prop);
        return prop;
    }
}
