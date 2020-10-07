package com.wkrj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//开启缓存
@EnableCaching
//开启声明式事务
@EnableTransactionManagement
//扫描mapper
@MapperScan("com.wkrj.**.dao")
@SpringBootApplication
public class WebbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebbootApplication.class, args);
    }

}
