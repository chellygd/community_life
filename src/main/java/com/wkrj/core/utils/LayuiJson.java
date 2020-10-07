package com.wkrj.core.utils;

import cn.hutool.json.JSONObject;

/**
 * 基于Layui前后端分离，请求用到的Json格式数据
 * @author ziro
 */
public class LayuiJson {
	
	private Integer code = 0;//状态码（可自定义，已有：0请求成功；1001登录失效；
	private String msg;//反馈消息
	private String count;//总记录数
	private Object data;//其他数据
	private boolean success = false;
	
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getJsonStr() {
		JSONObject obj = new JSONObject();
		obj.put("code", this.getCode());
		obj.put("msg", this.getMsg());
		obj.put("count", this.getCount());
		obj.put("data", this.getData());
		obj.put("success", this.isSuccess());
		return obj.toString();
	}
}
