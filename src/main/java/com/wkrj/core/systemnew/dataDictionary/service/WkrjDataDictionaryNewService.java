package com.wkrj.core.systemnew.dataDictionary.service;

import com.wkrj.core.systemnew.dataDictionary.bean.WkrjDataDictionaryNew;

import java.util.List;
import java.util.Map;


public interface WkrjDataDictionaryNewService {

	List<Map<String, Object>> getDataDictionary(String parentId);

	List<Map<String, Object>> getDataDictionaryTree(String parentId, String id);

	boolean addDataDictionary(WkrjDataDictionaryNew dd);

	boolean updateDataDictionary(WkrjDataDictionaryNew dd);

	int getDataDictionaryChildCount(String id);

	boolean delDataDictionary(String id);
    
	
}
