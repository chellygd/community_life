package ${packageName}.bean;

<#if specialFields?contains("Date")>
import java.util.Date;
</#if>
<#if specialFields?contains("Timestamp")>
import java.sql.Timestamp;
</#if>
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * ${tableRemark} - 实体类
 *
 * @author ${author}
 * @version ${version}
 * @date ${date}
 */
@TableName("${tableName}")
@Data
public class ${tableNameHump}Entity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

<#list columnList as column>
<#if column.isPrimaryKey == 1>
	/**
	 *  ${column.columnRemark}
	 */
	@TableId(value = "${column.columnName}")
	private ${column.columnTypeJava} ${column.columnNameHump};
<#else>
<#if column.columnTypeJava == 'Date'>
	/**
	 *  ${column.columnRemark}
	 */
	@TableField(value = "${column.columnName}")
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss", timezone="GMT+8")
	private ${column.columnTypeJava} ${column.columnNameHump};
<#else>
	/**
	 *  ${column.columnRemark}
	 */
	@TableField(value = "${column.columnName}")
	private ${column.columnTypeJava} ${column.columnNameHump};
</#if>
</#if>
</#list>

}
