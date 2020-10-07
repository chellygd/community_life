package com.wkrj.core.systemnew.code.service;

import com.wkrj.core.systemnew.code.bean.DataBaseTable;
import com.wkrj.core.systemnew.code.bean.TemplateModel;
import com.wkrj.core.systemnew.code.utils.DatabaseUtil;
import com.wkrj.core.systemnew.code.utils.FreeMarkerUtils;
import com.wkrj.core.systemnew.code.utils.Thymeleaf2HtmlUtils;
import com.wkrj.core.utils.ReadPropertiesUtil;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 代码生成器 - 业务实现
 *
 * @author Harry
 * @version 1.0
 * @date 2020-08-01
 */
public class IllusionGenerateBusiness {

    /**
     * 生成文件
     *
     * @param types            类型标准（一般为TABLE,即获取所有类型为TABLE的表，其他类型为"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM等）
     * @param tableNamePattern 表名集合（可以设置为null，就是获取所有的表）
     * @param packageName      包名
     * @param author           生成用户
     * @return
     */
    public static void generatingFiles(String[] types, List<String> tableNamePattern, String packageName, String author) throws Exception {
        //获取数据库连接
        Connection con = DatabaseUtil.getDBConnect();

        //项目路径
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\";
        //转换path
        path = path + packageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator)) + "\\";
        System.out.println("生成路径：" + path);


        //获取所数据库表
        List<DataBaseTable> tableList = DatabaseUtil.getDatabaseData(con, tableNamePattern, types);

        //时间格式化工具
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (DataBaseTable model : tableList) {
            //生成model数据对象
            TemplateModel templateModel = new TemplateModel();
            templateModel.setAuthor(author);
            templateModel.setColumnList(model.getColumnList());
            templateModel.setDate(sdf.format(new Date()));
            templateModel.setPackageName(packageName);
            templateModel.setSpecialFields(model.getSpecialFields() == null ? "" : model.getSpecialFields());
            templateModel.setTableName(model.getTableName());
            templateModel.setTableNameHump(model.getTableNameHump());
            templateModel.setTableRemark(model.getTableRemark());
            templateModel.setVersion("1.0.0");
            templateModel.setPrimaryKeyNameHump(model.getPrimaryKeyNameHump());

            //生成后台文件
            createBean(templateModel, path);
            createController(templateModel, path);
            createDao(templateModel, path);
            createMapper(templateModel, path);
            createService(templateModel, path);
            createServiceImpl(templateModel, path);
            //生成静态页
            createListHtml(templateModel);
            createAddHtml(templateModel);
            createUpdateHtml(templateModel);
        }
    }

    /**
     * 生成list列表页面的html
     *
     * @param templateModel
     */
    private static void createListHtml(TemplateModel templateModel) {
        Map<String, Object> content = new HashMap<>();
        content.put("data", templateModel);
        Thymeleaf2HtmlUtils.createCrudHtml("list", templateModel.getTableNameHump() + "\\list.html", content);
    }

    /**
     * 生成add页面的html
     *
     * @param templateModel
     */
    private static void createAddHtml(TemplateModel templateModel) {
        Map<String, Object> content = new HashMap<>();
        content.put("data", templateModel);
        Thymeleaf2HtmlUtils.createCrudHtml("add", templateModel.getTableNameHump() + "\\add.html", content);
    }

    /**
     * 生成update页面的html
     *
     * @param templateModel
     */
    private static void createUpdateHtml(TemplateModel templateModel) {
        Map<String, Object> content = new HashMap<>();
        content.put("data", templateModel);
        Thymeleaf2HtmlUtils.createCrudHtml("update", templateModel.getTableNameHump() + "\\update.html", content);
    }

    /**
     * 生成bean
     *
     * @param model 数据集合
     * @param path  地址前缀
     * @return success/error
     */
    private static void createBean(TemplateModel model, String path) throws Exception {
        //拼接路径
        String filePath = path + "bean\\" + model.getTableNameHump() + "Entity.java";
        //生成文件
        generateFileByTemplate("Bean.ftl", filePath, model);
    }

    /**
     * 生成Controller
     *
     * @param model 数据集合
     * @param path  地址前缀
     * @return success/error
     */
    private static void createController(TemplateModel model, String path) throws Exception {
        //拼接路径
        String filePath = path + "controller\\" + model.getTableNameHump() + "Controller.java";
        //生成文件
        generateFileByTemplate("Controller.ftl", filePath, model);
    }

    /**
     * 生成Dao
     *
     * @param model 数据集合
     * @param path  地址前缀
     * @return success/error
     */
    private static void createDao(TemplateModel model, String path) throws Exception {
        //拼接路径
        String filePath = path + "dao\\" + model.getTableNameHump() + "Dao.java";
        //生成文件
        generateFileByTemplate("Dao.ftl", filePath, model);
    }

    /**
     * 生成Mapper
     *
     * @param model 数据集合
     * @param path  地址前缀
     * @return success/error
     */
    private static void createMapper(TemplateModel model, String path) throws Exception {
        //拼接路径
        String filePath = path + "dao\\mapper\\" + model.getTableNameHump() + "Mapper.xml";
        //生成文件
        generateFileByTemplate("Mapper.ftl", filePath, model);
    }

    /**
     * 生成Service
     *
     * @param model 数据集合
     * @param path  地址前缀
     * @return success/error
     */
    private static void createService(TemplateModel model, String path) throws Exception {
        //拼接路径
        String filePath = path + "service\\" + model.getTableNameHump() + "Service.java";
        //生成文件
        generateFileByTemplate("Service.ftl", filePath, model);
    }

    /**
     * 生成Service
     *
     * @param model 数据集合
     * @param path  地址前缀
     * @return success/error
     */
    private static void createServiceImpl(TemplateModel model, String path) throws Exception {
        //拼接路径
        String filePath = path + "service\\impl\\" + model.getTableNameHump() + "ServiceImpl.java";
        //生成文件
        generateFileByTemplate("ServiceImpl.ftl", filePath, model);
    }

    /**
     * 根据模版生成文件
     *
     * @param templateName 模版名
     * @param filePath     生成路径
     * @param dataMap
     */
    private static void generateFileByTemplate(String templateName, String filePath, TemplateModel dataMap) throws Exception {
        //判断文件是否存在
        String dirFilePath = filePath.substring(0, filePath.lastIndexOf("\\"));
        File dirFile = new File(dirFilePath);
        //不存在则创建
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //获取模版
        Template template = FreeMarkerUtils.getTemplate(templateName);
        //创建流
        FileOutputStream fos = new FileOutputStream(filePath);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"), 10240);
        //生成文件
        template.process(dataMap, out);
    }
}
