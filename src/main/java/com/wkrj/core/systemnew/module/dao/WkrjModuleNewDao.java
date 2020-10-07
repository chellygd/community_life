package com.wkrj.core.systemnew.module.dao;

import com.wkrj.core.systemnew.module.bean.WkrjModuleNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface WkrjModuleNewDao {

    /**
     * 判断是否还有子节点，如果大于0说明有
     *
     * @param parentId
     * @return
     */
    public int getGridInfoChildCount(String parentId);

    public List<WkrjModuleNew> getGridInfo(@Param("parentId") String parentId, @Param("module_id") String module_id);

    public boolean addModule(WkrjModuleNew module);

    public boolean updateModule(WkrjModuleNew module);

    public boolean delModule(String id);

    public Map<String, Object> getWkrjModuleById(@Param("module_id") String module_id);

    public List<WkrjModuleNew> getDisplayGridInfo(String parentId);

    public int getDisplayGridInfoChildCount(String module_id);

    public List<Map<String, Object>> getIcon();

}
