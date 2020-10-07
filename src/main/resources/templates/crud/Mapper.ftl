<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- ${tableRemark} - 数据实现 -->
<mapper namespace="${packageName}.dao.${tableNameHump}Dao">
	<#--  ${tableRemark}映射类型  -->
	<#--<resultMap id="${tableNameHump}Entity" type="${packageName}.bean.${tableNameHump}Entity">
		<#list columnList as column>
		<#if column.isPrimaryKey == 1>
		<id property="${column.columnNameHump}" column="${column.columnName}"/>
		<#else>
		<result property="${column.columnNameHump}" column="${column.columnName}"/>
		</#if>
		</#list>
	</resultMap>-->

	<!--  获取所有数据  -->
	<select id="getAllData" parameterType="java.util.Map" resultType="${packageName}.bean.${tableNameHump}Entity">
		SELECT
			*
		FROM
			${tableName} t
		WHERE
			1 = 1
		<#list columnList as column>
			<if test="${column.columnNameHump} != null and '' != ${column.columnNameHump}">
				AND t.${column.columnName} = <#noparse>#{</#noparse>${column.columnNameHump}<#noparse>}</#noparse>
			</if>
		</#list>
		<if test="offset >= 0 and limit > 0">
			limit <#noparse>#{</#noparse>offset<#noparse>}</#noparse>,<#noparse>#{</#noparse>limit<#noparse>}</#noparse>
		</if>
	</select>

	<!--  获取所有数据集合  -->
	<select id="getAllDataCount" parameterType="java.util.Map" resultType="java.lang.Long">
		SELECT
			count(*)
		FROM
			${tableName} t
		WHERE
			1 = 1
		<#list columnList as column>
			<if test="${column.columnNameHump} != null and '' != ${column.columnNameHump}">
				AND t.${column.columnName} = <#noparse>#{</#noparse>${column.columnNameHump}<#noparse>}</#noparse>
			</if>
		</#list>
	</select>

</mapper>