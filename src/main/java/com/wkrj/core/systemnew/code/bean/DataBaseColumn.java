package com.wkrj.core.systemnew.code.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据库表字段对象
 *
 * @author Harry
 * @version 1.0
 * @date 2020-08-01
 */
@Data
public class DataBaseColumn implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  是否主键
	 */
	private Integer isPrimaryKey;
	/**
	 *  字段名
	 */
	private String columnName;
	/**
	 *  驼峰式字段名
	 */
	private String columnNameHump;
	/**
	 *  字段类型
	 */
	private String columnType;
	/**
	 *  数据字段类型对应java类型
	 */
	private String columnTypeJava;
	/**
	 *  字段长度
	 */
	private String columnSize;
	/**
	 *  字段备注
	 */
	private String columnRemark;
}
