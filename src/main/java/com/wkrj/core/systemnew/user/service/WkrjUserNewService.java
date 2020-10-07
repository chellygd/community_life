package com.wkrj.core.systemnew.user.service;

import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;

import java.util.List;
import java.util.Map;


public interface WkrjUserNewService {

	List<WkrjUserNew> getUserList(int offset, int limit, String deptId,
                                  String userName, String dept_id);

	long getUserList(String deptId, String userName, String dept_id);

	List<WkrjRoleNew> getRoleListByUserId(String user_id);

	List<Map<String, Object>> getRoleList();

	String addUser(WkrjUserNew perm, String role);

	String updateUser(WkrjUserNew module, String role);

	boolean delUser(String string);

	boolean forbiddenUser(String id, String flag);

	boolean repeatPw(String id, String string);

	boolean updatePassword(String userId, String md5Encode);

	WkrjUserNew getUserInfoById(String user_id);

	boolean updateUserInfo(WkrjUserNew module);

	WkrjUserNew findUserInfoByName(String loginName);

	WkrjUserNew findUserInfoByName(String uName, String password);
    

}
