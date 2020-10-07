package com.wkrj.core.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Properties;

/**
 * 读取配置文件，工具类
 *
 * @author ziro
 * @date 2020/4/17 17:30
 */
public class ReadPropertiesUtil {

    private static String PROFILES_ACTIVE = readActive();

    /**
     * 读取默认配置文件（dbconfig.properties）的key
     *
     * @param key
     * @return
     */
    public static String readProperty(String key) {
        ClassPathResource resource = new ClassPathResource("application-" + PROFILES_ACTIVE + ".properties");
        Properties prop = new Properties();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            prop.load(bf);
            if (prop.getProperty(key) == null || "".equals(prop.getProperty(key))) {
                resource = new ClassPathResource("application.properties");
                bf = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                prop.load(bf);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "读取文件出错";
        }
        return prop.getProperty(key);
    }

    /**
     * 读取自定义配置文件的key
     *
     * @param key
     * @param proFileName 配置文件名
     * @return
     */
    public static String readProperty(String key, String proFileName) {
        ClassPathResource resource = new ClassPathResource(proFileName);
        Properties prop = new Properties();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            prop.load(bf);
        } catch (IOException e) {
            e.printStackTrace();
            return "读取文件出错";
        }
        return prop.getProperty(key);
    }

    /**
     * 读取自定义文件内容
     *
     * @param filename 根目录配置文件名
     * @return
     */
    public static String readFile(String filename) {
        return readFile(filename, null);
    }

    /**
     * 读取自定义文件内容( # 开头的注释行不读取)
     *
     * @param filename 文件名
     * @param filepath 相对根路径地址
     * @return
     */
    public static String readFile(String filename, String filepath) {
        filepath = filepath == null ? "" : filepath;
        ClassPathResource resource = new ClassPathResource(filepath + filename);
        String result = "";
        try {
            //判断文件是否存在
            InputStreamReader read = new InputStreamReader(resource.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = "";
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (!"".equals(lineTxt)) {
                    if (lineTxt.indexOf("#") == 0) {
                        // # 开头的注释行，不读取
                    } else {
                        result += lineTxt + ",";
                    }
                }
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "读取文件出错";
        }
        return result;
    }

    private static String readActive() {
        ClassPathResource resource = new ClassPathResource("application.properties");
        Properties prop = new Properties();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            prop.load(bf);
        } catch (IOException e) {
            e.printStackTrace();
            return "读取文件出错";
        }
        return prop.getProperty("spring.profiles.active");
    }

    public static void main(String[] args) {
        /*try {
            System.out.println(ReadPropertiesUtil.readProperty("xxx"));
            System.out.println(ReadPropertiesUtil.readProperty("server.servlet.context-path"));
            System.out.println(ReadPropertiesUtil.readProperty("server.port"));
            System.out.println(ReadPropertiesUtil.readProperty("spring.session.store-type"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
