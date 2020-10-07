package com.wkrj.core.systemnew.module.service;

import com.wkrj.core.systemnew.module.bean.WkrjModuleNew;

import java.util.List;
import java.util.Map;


public interface WkrjModuleNewService {

	List<Map<String, Object>> getGridInfo(String string);

	List<Map<String, Object>> getGridInfo_tree(String string, String module_id);

	boolean addModule(WkrjModuleNew module);

	boolean updateModule(WkrjModuleNew module);

	int checkIsHaveChildren(String id);

	boolean delModule(String id);

	List<Map<String, Object>> getDisplayGridInfo(String string);

	List<Map<String, Object>> getIcon();

	List<Map<String, Object>> getGridInfo_gly(String string);
    
}
