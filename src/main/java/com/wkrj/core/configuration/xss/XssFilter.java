package com.wkrj.core.configuration.xss;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static java.util.regex.Pattern.compile;

/**
 * @author ziro
 * @date 2020/6/6 14:20
 */
public class XssFilter implements Filter {

    FilterConfig filterConfig = null;
    private String urls;

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    //忽略的url
    private String[] ingoreUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        urls = filterConfig.getInitParameter("excludes");
        if (null != urls && !"".equals(urls)) {
            ingoreUrls = urls.split(",");
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String thisurl = ((HttpServletRequest) request).getRequestURI();
        thisurl = removeCtx(thisurl, ((HttpServletRequest) request).getContextPath());
        boolean sus = false;
        if (null != ingoreUrls && ingoreUrls.length > 0) {
            for (String permit : ingoreUrls) {
                if (permit.indexOf("**") >= 0) {
                    //正则表达式相匹配 ^表示以什么开头   .*  表示0-n个字符
                    if (compile("^" + permit.replace("**", ".*")).matcher(thisurl).find()) {
                        sus = true;
                        break;
                    }
                } else {
                    if (permit.trim().equals(thisurl)) {
                        sus = true;
                        break;
                    }
                }
            }
        }
        if (sus) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
        }
    }

    /**
     * 获取当前URL
     *
     * @param url
     * @param contextPath
     * @return
     */
    private static String removeCtx(String url, String contextPath) {
        url = url.trim();
        if (null == contextPath || "".equals(contextPath)) {
            return url;
        }
        if (null == url || "".equals(url)) {
            return "";
        }
        if (url.startsWith(contextPath)) {
            url = url.replaceFirst(contextPath, "");
        }
        return url;
    }
}
