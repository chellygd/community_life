package com.wkrj.core.systemnew.code.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库表对象
 *
 * @author Harry,ziro
 * @version 1.0
 * @date 2020-08-01
 */
@Data
public class DataBaseTable implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    private String tableName;
    /**
     * 驼峰式表名
     */
    private String tableNameHump;
    /**
     * 表类型
     */
    private String tableType;
    /**
     * 表所属数据库
     */
    private String tableCat;
    /**
     * 表备注
     */
    private String tableRemark;
    /**
     * 表字段信息
     */
    private List<DataBaseColumn> columnList;
    /**
     * 特殊类型字段
     */
    private String specialFields;
    /**
     * 驼峰式主键名
     */
    private String primaryKeyNameHump;
}
