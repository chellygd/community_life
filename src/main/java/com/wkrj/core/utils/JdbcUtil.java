package com.wkrj.core.utils;

//import com.alibaba.druid.pool.DruidDataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author ziro
 * @date 2020/4/27 15:59
 */
public class JdbcUtil {
    /**
     * 获取JdbcTemplate用于数据操作
     *
     * @return
     */
    public static JdbcTemplate getJdbcTemplate() {
        /*
        //1使用阿里druid数据源
        DruidDataSource dataSource = new DruidDataSource();
        // 设置数据源属性参数
        dataSource.setUrl(ReadPropertiesUtil.readProperty("spring.datasource.url"));
        dataSource.setDriverClassName(ReadPropertiesUtil.readProperty("spring.datasource.driver-class-name"));
        dataSource.setUsername(ReadPropertiesUtil.readProperty("spring.datasource.username"));
        dataSource.setPassword(ReadPropertiesUtil.readProperty("spring.datasource.password"));
        // 获取spring的JdbcTemplate
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // 设置数据源
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;*/

        //2使用hikaris数据源
        HikariDataSource dataSource = new HikariDataSource();
        // 设置数据源属性参数
        dataSource.setJdbcUrl(ReadPropertiesUtil.readProperty("spring.datasource.url"));
        dataSource.setDriverClassName(ReadPropertiesUtil.readProperty("spring.datasource.driver-class-name"));
        dataSource.setUsername(ReadPropertiesUtil.readProperty("spring.datasource.username"));
        dataSource.setPassword(ReadPropertiesUtil.readProperty("spring.datasource.password"));
        // 获取spring的JdbcTemplate
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // 设置数据源
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
