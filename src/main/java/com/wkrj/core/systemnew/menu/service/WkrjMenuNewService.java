package com.wkrj.core.systemnew.menu.service;

import com.wkrj.core.systemnew.module.bean.WkrjModuleNew;

import java.util.List;
import java.util.Map;


public interface WkrjMenuNewService {

	List<Map<String, Object>> getGridInfo(String string);

	boolean addMenu(WkrjModuleNew module);

	boolean updateMenu(WkrjModuleNew module, String yFileName);

	boolean delMenu(String id, String icon);

	int checkIsHaveChildren(String id);

	List<Map<String, Object>> getLeftMenuByRole(String string, String roleIds);
	
}
