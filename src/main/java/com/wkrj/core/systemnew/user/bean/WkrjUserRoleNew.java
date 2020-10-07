package com.wkrj.core.systemnew.user.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 岗位模块表 对应数据库表：wkrj_sys_user_role
 * 
 * @author ziro
 * 
 */
@Data
public class WkrjUserRoleNew implements Serializable {
	private static final long serialVersionUID = -1808121390235512662L;

	private String userId;//用户Id
	private String roleId;// 角色Id
}
