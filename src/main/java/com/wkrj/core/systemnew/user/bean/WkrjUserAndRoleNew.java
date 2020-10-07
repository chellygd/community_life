package com.wkrj.core.systemnew.user.bean;

import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import lombok.Data;

import java.io.Serializable;


/**
 * 岗位模块表 对应数据库表：wkrj_sys_user_role
 * 
 * @author ziro
 * 
 */
@Data
public class WkrjUserAndRoleNew implements Serializable {
	private static final long serialVersionUID = -3587005092683236438L;

	private WkrjUserNew user;
	private WkrjRoleNew role;
}
