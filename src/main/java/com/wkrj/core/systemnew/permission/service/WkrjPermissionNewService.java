package com.wkrj.core.systemnew.permission.service;

import com.wkrj.core.systemnew.permission.bean.WkrjPermissionNew;

import java.util.List;


public interface WkrjPermissionNewService {

	boolean delPermissionByModuleId(String id);

	public boolean delPermission(String id, String icon);

	List<WkrjPermissionNew> getPermissionList(int offset, int limit,
                                              String module_id);

	long getPermissionList(String module_id);

	boolean checkIsHavePermission(String perm_flag, String id);

	boolean addPermission(WkrjPermissionNew perm);

	boolean updatePermission(WkrjPermissionNew perm);

	boolean delPermission(String id);
}