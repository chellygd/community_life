package com.wkrj.core.configuration.xss;

import com.wkrj.core.utils.ReadPropertiesUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * xss攻击配置文件
 *
 * @author ziro
 * @date 2020/6/6 14:20
 */
@Configuration
public class XssFilterConfigurer {

    /**
     * xss过滤拦截器
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(Integer.MAX_VALUE - 1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        //excludes用于配置不需要参数过滤的请求url
        initParameters.put("excludes", ReadPropertiesUtil.readFile("xss_ignore.properties"));
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
