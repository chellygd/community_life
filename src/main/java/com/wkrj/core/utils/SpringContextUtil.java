package com.wkrj.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Locale;

public class SpringContextUtil implements ApplicationContextAware{
	
	   private static ApplicationContext context;
	    @SuppressWarnings("static-access")
	    @Override
	    public void setApplicationContext(ApplicationContext contex)
	            throws BeansException {
	        context=contex;
	    }
	    public static Object getBean(String beanName){
	        return context.getBean(beanName);
	    }
	 
	     
	    public static String getMessage(String key){
	        return context.getMessage(key, null, Locale.getDefault());
	    }
	
}
