package com.wkrj.core.systemnew.role.service;

import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;

import java.util.List;


public interface WkrjRoleNewService {

	List<WkrjRoleNew> getRoleList(int offset, int limit, String dept_id);

	long getRoleList(String dept_id);

	boolean addRole(WkrjRoleNew perm);

	boolean updateRole(WkrjRoleNew module);

	boolean delRole(String string);

	boolean copyRole(String role_id);

	Object getMenuPermission(String role_id, String parentId);

	boolean setMenuPermission(String menu_ids, String perm_ids, String rolerId,
                              String menu_order);

	public String getAllPermission();

	public String getRolePermissionByRoleId(String id);
    

}
