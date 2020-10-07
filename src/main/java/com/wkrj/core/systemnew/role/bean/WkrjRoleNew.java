package com.wkrj.core.systemnew.role.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能模块表 对应数据库表：wkrj_sys_role
 * 
 * @author zhaoxiaohua
 * 
 */
@Data
public class WkrjRoleNew implements Serializable {
	private static final long serialVersionUID = -7242302110016200702L;

	private String roleId;
	private String roleName;// 角色名称
	private String roleType;// 角色类型1、开发管理员 2、普通管理员 默认是普通管理员
	private String roleDept;// 角色所在部门
	private String roleOther;// 角色备注
	private String roleIsdel;//删除标识，默认0，删除1
}
