package com.wkrj.core.component.jwt;

import java.io.Serializable;
import java.util.Date;

/**
 * Token
 * @author wxf
 *
 */
public class Token implements Serializable {
	
	private static final long serialVersionUID = 8806470632703690620L;
	/** ID*/
	private String id;
	/** 用户ID*/
	private String userId;
	/** 店铺ID*/
	private String storeId;
	/** 用户Token*/
	private String accessToken;
	/** 过期时间*/
	private Date expireTime;
	/** Token状态*/
	private String tokenStatus;
	/** 创建时间*/
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public String getTokenStatus() {
		return tokenStatus;
	}
	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "Token [id=" + id + ", userId=" + userId + ", storeId="
				+ storeId + ", accessToken=" + accessToken + ", expireTime="
				+ expireTime + ", tokenStatus=" + tokenStatus + ", createTime="
				+ createTime + "]";
	}
}
