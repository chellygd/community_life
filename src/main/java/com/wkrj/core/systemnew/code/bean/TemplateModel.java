package com.wkrj.core.systemnew.code.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 模版数据对象
 *
 * @author Harry
 * @version 1.0
 * @date 2020-08-06
 */
@Data
public class TemplateModel implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     *  包名
     */
    private String packageName;
    /**
     *  特殊字段
     */
    private String specialFields;
    /**
     *  表备注
     */
    private String tableRemark;
    /**
     *  创建人
     */
    private String author;
    /**
     *  版本号
     */
    private String version;
    /**
     *  创建时间
     */
    private String date;
    /**
     *  表名
     */
    private String tableName;
    /**
     *  驼峰式表名
     */
    private String tableNameHump;
    /**
     * 驼峰式主键名
     */
    private String primaryKeyNameHump;
    /**
     *  包名
     */
    private List<DataBaseColumn> columnList;
}
