package com.wkrj.core.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 事务配置
 *
 * @author ziro
 * @date 2020/4/28 14:08
 */
@Configuration
public class TransactionalConfig implements TransactionManagementConfigurer {

    @Resource(name = "txManager1")
    private PlatformTransactionManager txManager1;

    /**
     * 创建事务管理器1
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "txManager1")
    public PlatformTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager1;
    }
}
