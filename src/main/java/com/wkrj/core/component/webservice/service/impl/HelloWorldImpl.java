package com.wkrj.core.component.webservice.service.impl;

import com.wkrj.core.component.webservice.service.HelloWorld;

import javax.jws.WebService;

@SuppressWarnings("restriction")
@WebService(targetNamespace="zxh")
public class HelloWorldImpl implements HelloWorld{

	
	public HelloWorldImpl(){
		System.out.println("启动了Webservice");
	}
	
	@Override
	public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }
	
}
