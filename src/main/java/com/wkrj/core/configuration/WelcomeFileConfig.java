package com.wkrj.core.configuration;

import com.wkrj.core.utils.ReadPropertiesUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ziro
 * @date 2020/7/20 16:22
 */
@Configuration
public class WelcomeFileConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:" + ReadPropertiesUtil.readProperty("wkrj.security.loginurl"));
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
