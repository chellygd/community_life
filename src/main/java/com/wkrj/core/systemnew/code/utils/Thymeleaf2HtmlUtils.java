package com.wkrj.core.systemnew.code.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.util.Map;

/**
 * @author ziro
 * @date 2020/8/29 9:42
 */
public class Thymeleaf2HtmlUtils {

    public static void main(String[] args) {

    }

    /**
     * 生成crud静态文件，辅助后台代码生成器使用
     *
     * @param templateName 模板名称
     * @param filepath     待生成的模块路径
     * @param content      模板动态内容
     */
    public static void createCrudHtml(String templateName, String filepath, Map<String, Object> content) {
        //默认生成到项目的：   /main/views/wkrj/  路径中
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\main\\views\\wkrj\\" + filepath;
        System.out.println("静态文件生成路径：" + path);
        //默认模板前缀
        String templatePrefix = "templates\\crudhtml\\";
        //默认模板后缀
        String templateSurfix = ".html";
        //开始生成
        createHtmlFile(templatePrefix, templateName, templateSurfix, path, content);
    }

    /**
     * 生成静态文件
     *
     * @param templatePrefix 模板前缀（路径）
     * @param templateName   模板名称
     * @param templateSurfix 模板后缀（.html）
     * @param tofilepath     生成地址
     * @param content        动态内容
     */
    public static void createHtmlFile(String templatePrefix, String templateName, String templateSurfix, String tofilepath, Map<String, Object> content) {
        try {
            //设置模板位置
            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix(templatePrefix);
            resolver.setSuffix(templateSurfix);
            //构造模板引擎
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);
            //设置填充内容
            Context context = new Context();
            context.setVariables(content);
            //判断文件是否存在
            String dirFilePath = tofilepath.substring(0, tofilepath.lastIndexOf("\\"));
            File dirFile = new File(dirFilePath);
            //不存在则创建
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            //创建流
            FileOutputStream fos = new FileOutputStream(tofilepath);
            Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
            templateEngine.process(templateName, context, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
