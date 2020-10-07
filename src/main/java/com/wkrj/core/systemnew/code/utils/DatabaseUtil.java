package com.wkrj.core.systemnew.code.utils;

import com.wkrj.core.systemnew.code.bean.DataBaseColumn;
import com.wkrj.core.systemnew.code.bean.DataBaseTable;
import com.wkrj.core.utils.ReadPropertiesUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 代码生成器 - 数据库工具类
 *
 * @author Harry, ziro
 * @version 1.0
 * @date 2020-08-01
 */
public class DatabaseUtil {
    //日志打印
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);
    //数据库类型（MySQL、Oracle，目前仅支持这两个，目前暂时没有什么用）
    private static String dataBaseType = ReadPropertiesUtil.readProperty("spring.datasource.database.type");
    //模式（可以理解为数据库登录名，MySQL没有硬性要求，Oracle要求为大写登录名）
    private static String schemaPattern = ReadPropertiesUtil.readProperty("spring.datasource.username").toUpperCase();
    //数据库连接信息
    private static String url = ReadPropertiesUtil.readProperty("spring.datasource.url");
    private static String driverClassName = ReadPropertiesUtil.readProperty("spring.datasource.driver-class-name");
    private static String username = ReadPropertiesUtil.readProperty("spring.datasource.username");
    private static String password = ReadPropertiesUtil.readProperty("spring.datasource.password");

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getDBConnect() throws Exception {
        try {
            //创建数据库连接对象
            Connection con = null;
            //创建连接对象
            HikariDataSource dataSource = new HikariDataSource();
            // 设置数据源属性参数
            dataSource.setJdbcUrl(url);
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            Properties properties = new Properties();
            properties.setProperty("remarks", "true");
            properties.setProperty("useInformationSchema", "true");
            dataSource.setDataSourceProperties(properties);

            // 建立了连接
            con = dataSource.getConnection();
            //返回连接对象
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("创建连接对象时出现异常：" + e);
            return null;
        }
    }

    /**
     * 获取指定的数据库表数据信息
     *
     * @param con              数据库连接
     * @param tableNamePattern 表名集合（可以设置为null，就是获取所有的表）
     * @param types            类型标准（一般为TABLE,即获取所有类型为TABLE的表，其他类型为"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM等）
     * @return
     */
    public static List<DataBaseTable> getDatabaseData(Connection con, List<String> tableNamePattern, String[] types) {
        try {
            //获取数据操作对象
            DatabaseMetaData dbMetaData = con.getMetaData();
            //所有表数据承载体
            List<DataBaseTable> tableList = new ArrayList<>();

            //遍历需要查询的表
            for (String tName : tableNamePattern) {
                //获取所有表的信息
                ResultSet rs = dbMetaData.getTables(con.getCatalog(), schemaPattern, tName, types);

                //遍历所有表获取表格信息
                while (rs.next()) {
                    //创建数据承载对象
                    DataBaseTable table = new DataBaseTable();

                    /**
                     * 获取表的基本数据
                     */
                    //表名
                    String tableName = rs.getString("TABLE_NAME");
                    //表类型
                    String tableType = rs.getString("TABLE_TYPE");
                    //表所属数据库
                    String tableCat = rs.getString("TABLE_CAT");
                    //表备注
                    String tableRemark = rs.getString("REMARKS");

                    //封装数据
                    table.setTableName(tableName);
                    table.setTableType(tableType);
                    table.setTableCat(tableCat);
                    table.setTableRemark(tableRemark);
                    table.setTableNameHump(CurrencyUtil.lineToHump(tableName, true));

                    /**
                     * 获取表字段的数据
                     */
                    //获取主键字段
                    ResultSet pk = dbMetaData.getPrimaryKeys(con.getCatalog(), null, tableName);
                    //主键字段名
                    String primaryName = "";

                    while (pk.next()) {
                        //获取字段名
                        String columnName = pk.getString("COLUMN_NAME");
                        //获取约束名
                        String columnPKName = pk.getString("PK_NAME");
                        //如果是主键
                        if ("PRIMARY".equals(columnPKName)) {
                            primaryName = columnName;
                            //设置主键名称
                            table.setPrimaryKeyNameHump(CurrencyUtil.lineToHump(columnName, true));
                        }
                    }

                    //获取表字段信息
                    ResultSet rsColimns = dbMetaData.getColumns(con.getCatalog(), "%", rs.getString("TABLE_NAME"), "%");
                    //字段集合
                    List<DataBaseColumn> columnList = new ArrayList<>();

                    //循环遍历所有表字段信息
                    while (rsColimns.next()) {
                        //创建数据承载对象
                        DataBaseColumn column = new DataBaseColumn();

                        //字段名
                        String columnName = rsColimns.getString("COLUMN_NAME");
                        //字段类型
                        String columnType = rsColimns.getString("TYPE_NAME");
                        //字段长度
                        String columnSize = rsColimns.getString("COLUMN_SIZE");
                        //字段备注
                        String columnRemark = rsColimns.getString("REMARKS");

                        //判断当前字段是否等于主键字段名
                        if (columnName.equals(primaryName)) {
                            column.setIsPrimaryKey(1);
                        } else {
                            column.setIsPrimaryKey(0);
                        }

                        //封装数据
                        column.setColumnName(columnName);
                        column.setColumnType(columnType);
                        column.setColumnSize(columnSize);
                        column.setColumnRemark(columnRemark);
                        column.setColumnNameHump(CurrencyUtil.lineToHump(columnName, false));
                        column.setColumnTypeJava(typeMapping(columnType));

                        //获取特殊字段
                        String specialField = getSpecialFields(columnType);
                        //如果特殊自动不是空，并且在表的特殊字段集合中不存在，那么就添加该字段
                        if (!"".equals(specialField)) {
                            if (table.getSpecialFields() == null || "".equals(table.getSpecialFields())) {
                                table.setSpecialFields(specialField);
                            } else {
                                if (!table.getSpecialFields().contains(specialField)) {
                                    table.setSpecialFields(table.getSpecialFields() + "," + specialField);
                                }
                            }
                        }

                        //添加数据到集合
                        columnList.add(column);
                    }

                    //封装字段信息
                    table.setColumnList(columnList);
                    //封装进最终变量
                    tableList.add(table);
                }
            }

            //关闭数据库连接
            closeConnection(con);
            //返回map数据
            return tableList;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("获取表单信息时出现异常：" + e);
            return null;
        }
    }

    /**
     * 判断当前字段类是是否是特殊字段
     *
     * @param fieldType 字段类型
     * @return
     */
    private static String getSpecialFields(String fieldType) {
        if ("Date".equals(fieldType) || "DATETIME".equals(fieldType)) {
            return "Date";
        } else if ("Timestamp".equals(fieldType)) {
            return "Timestamp";
        } else {
            return "";
        }
    }

    /**
     * 转换数据库字段类型
     *
     * @param type 字段类型
     * @return
     */
    private static String typeMapping(String type) {
        //返回对象
        String typeName = "";
        //判断类型
        switch (type) {
            case "VARCHAR":
                typeName = "String";
                break;
            case "DATE":
                typeName = "Date";
                break;
            case "DATETIME":
                typeName = "Date";
                break;
            case "TIMESTAMP":
                typeName = "Timestamp";
                break;
            case "TINYINT":
                typeName = "Integer";
                break;
            case "INT":
                typeName = "Integer";
                break;
            case "BIT":
                typeName = "Integer";
                break;
            case "TEXT":
                typeName = "String";
                break;
            case "CHAR":
                typeName = "String";
                break;
            case "BIGINT":
                typeName = "Long";
                break;
            case "NUMERIC":
                typeName = "Double";
                break;
            case "INTEGER":
                typeName = "Integer";
                break;
            case "FLOAT":
                typeName = "Float";
                break;
            case "DOUBLE":
                typeName = "Double";
                break;
            case "DECIMAL":
                typeName = "String";
                break;
            default:
                typeName = "String";
                break;
        }
        //返回类型
        return typeName;
    }

    /**
     * 关闭连接
     *
     * @param con 数据库连接
     */
    private static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
