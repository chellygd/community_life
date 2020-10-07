package com.wkrj.core.systemnew.code.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码生成器 - 通用工具类
 *
 * @author Harry
 * @version 1.0
 * @date 2020-08-01
 */
public class CurrencyUtil {

    /**
     * 下划线转驼峰
     *
     * @param str             下划线命名
     * @param firstCapitalize 第一位是否大写
     * @return
     */
    public static String lineToHump(String str, boolean firstCapitalize) {
        //创建正则表达式
        Pattern linePattern = Pattern.compile("_(\\w)");
        //全部转换小写
        str = str.toLowerCase();

        //判断第一位是否需要大写
        if (firstCapitalize == true) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        }

        //格式化字符串
        Matcher matcher = linePattern.matcher(str);
        //转换数据
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);


        //返回数据
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param str 驼峰命名
     * @return
     */
    public static String humpToLine(String str) {
        //创建正则表达式
        Pattern humpPattern = Pattern.compile("[A-Z]");
        //格式化数据
        Matcher matcher = humpPattern.matcher(str);
        //转换数据
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        //返回数据
        return sb.toString();
    }

}
