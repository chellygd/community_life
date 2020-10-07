package com.wkrj.core.configuration.security;

import com.wkrj.core.utils.ReadPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Security配置文件
 * @author ziro
 * @date 2020/4/29 21:54
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment environment;
    @Autowired
    SecurityUserDetailsService securityUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //user Details Service验证
        auth.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1.basic登录方式
        //http.httpBasic()

        // 1.表单登录方式
        String loginurl = environment.getProperty("wkrj.security.loginurl");
        String checklogin = environment.getProperty("wkrj.security.checklogin");
        http.formLogin()
                //前端登录页
                .loginPage(loginurl)
                //登录controller
                .loginProcessingUrl(checklogin);
        // 2.请求授权
        String[] permitUrls = ReadPropertiesUtil.readFile(ReadPropertiesUtil.readProperty("wkrj.security.ignore")).split(",");
        http.authorizeRequests()
                //不需要权限认证的url
                .antMatchers(permitUrls).permitAll()
                //开启所有路径认证
                .anyRequest().authenticated();
        // 3.关闭跨站请求防护
        http.csrf().disable();
        // 4.iframe请求文件头
        http.headers().frameOptions().sameOrigin();
    }
}
