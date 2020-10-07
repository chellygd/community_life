package com.wkrj.core.systemnew.code;


import com.wkrj.core.systemnew.code.service.IllusionGenerateBusiness;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 代码生成器 - 入口
 *
 * @author Harry,ziro
 * @version 1.0
 * @date 2020-08-01
 */
public class IllusionGenerateMain {

    public static void main(String[] args) throws Exception {
        //1.数据库元数据属性
        //类型标准（一般为TABLE,即获取所有类型为TABLE的表，其他类型为"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM等）
        String[] types = new String[]{"TABLE"};

        //2.表名集合（可以设置为null，就是获取所有的表）
        List<String> tableNamePattern = new ArrayList<>();
        tableNamePattern.add("z_test");
        //tableNamePattern.add("wkrj_bus_experiment_calculation_standard");
        //tableNamePattern.add("wkrj_bus_experiment_equipment");

        //3.包路径
        String packagePath = "com.wkrj.module.test";

        //4.作者
        String auther = "ziro";

        //5.开始生成
        IllusionGenerateBusiness.generatingFiles(types, tableNamePattern, packagePath, auther);
        System.out.println("生成完成");
    }
}
