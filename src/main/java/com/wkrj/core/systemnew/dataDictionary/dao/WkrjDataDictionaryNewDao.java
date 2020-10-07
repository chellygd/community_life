package com.wkrj.core.systemnew.dataDictionary.dao;

import com.wkrj.core.systemnew.dataDictionary.bean.WkrjDataDictionaryNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface WkrjDataDictionaryNewDao {

	List<Map<String, Object>> getDataDictionaryTree(@Param("parentId") String parentId, @Param("id") String id);

	String getDataDictionaryChildMaxByPid(@Param("parentId") String parentId);

	boolean addDataDictionary(WkrjDataDictionaryNew dd);

	boolean updateDataDictionary(WkrjDataDictionaryNew dd);

	int getDataDictionaryChildCount(@Param("parentId") String parentId);

	boolean delDataDictionary(@Param("id") String id);
	

	
}
