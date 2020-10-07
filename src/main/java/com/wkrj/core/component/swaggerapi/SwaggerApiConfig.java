package com.wkrj.core.component.swaggerapi;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger生成API配置类
 *
 * @author ziro
 * @date 2019/7/24 17:28
 */
@Configuration
@EnableSwagger2
public class SwaggerApiConfig {

    /**
     * 创建api扫描规则，分组1
     * @return
     */
    @Bean
    public Docket api1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("test")
                .select()
//                这里采用包含注解的方式来确定要显示的接口(建议使用这种)
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.ant("/test/**"))
//                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 创建api扫描规则，分组2
     * @return
     */
    @Bean
    public Docket api2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("wkrjsystemnew")
                .select()
//                这里采用包含注解的方式来确定要显示的接口(建议使用这种)
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.ant("/wkrjsystemnew/**"))
                .build();
    }

    /**
     * api注册信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("潍科软件开放接口API文档")
                .description("api接口简介")
                .termsOfServiceUrl("http://www.wkrj.net")
//                .contact(contact)
                .version("1.0.0")
                .build();
    }
}
