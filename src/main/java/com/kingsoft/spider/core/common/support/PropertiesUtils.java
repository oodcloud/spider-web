package com.kingsoft.spider.core.common.support;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

/**
 * Created by wangyujie on 2018/1/3.
 */
public class PropertiesUtils {

    public static String readData(String filePath, String key) {
        InputStreamReader inputStreamReader = new InputStreamReader(PropertiesUtils.class.getResourceAsStream("/" + filePath));
        Properties props = new Properties();
        try {
            props.load(inputStreamReader);
            inputStreamReader.close();
            String value = props.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeData(String filePath, String key, String value) {
        InputStreamReader inputStreamReader = new InputStreamReader(PropertiesUtils.class.getResourceAsStream("/" + filePath));
        Properties prop = new Properties();
        try {
            prop.load(inputStreamReader);
            inputStreamReader.close();
            String basePath = PropertiesUtils.class.getResource("/").getPath();
            OutputStream fos = new FileOutputStream(basePath + filePath);
            prop.setProperty(key, value);
            prop.store(fos, "Update '" + key + "' value");
            fos.close();
        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for updating " + value + " value error");
        }
    }


}
