package com.wkrj.core.systemnew.department.dao;

import com.wkrj.core.systemnew.department.bean.WkrjDeptNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface WkrjDeptNewDao {

    List<WkrjDeptNew> getDeptTree(String parent_id);

    int getDeptChildCount(String dept_id);

    List<WkrjDeptNew> getDeptTree1(@Param("parent_id") String parent_id, @Param("isGly") boolean isGly,
                                   @Param("user_dept") String user_dept);

    String getDeptChildMaxByPid(String parentId);

    boolean addDept(WkrjDeptNew dept);

    boolean delDept_new(@Param("dept_id") String dept_id, @Param("dept_isdel") String dept_isdel);

    boolean updateDept(WkrjDeptNew dept);

    List<WkrjDeptNew> getDeptTree_new(@Param("parent_id") String parent_id, @Param("dept_id") String dept_id);

    List<Map<String, Object>> getDeptTree_lazy(@Param("parent_id") String parent_id);


}
