package com.wkrj.core.systemnew.department.service;

import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.systemnew.department.bean.WkrjDeptNew;

import java.util.List;
import java.util.Map;


public interface WkrjDeptNewService {

	Object getRoleList(String string, String dept_id);

	List<Map<String, Object>> getDeptAndSchoolGridList(String string,
                                                       boolean b, String user_dept);

	boolean addDept(WkrjDeptNew dept);

	LayuiJson updateDept(WkrjDeptNew dept, String yId);

	int getDeptChildCount(String id);

	boolean delDept(String id);

	List<Map<String, Object>> getDeptTree_lazy(String dept_id);
    
	
}
