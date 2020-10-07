package com.wkrj.core.systemnew.permission.dao;

import com.wkrj.core.systemnew.permission.bean.WkrjPermissionNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface WkrjPermissionNewDao {

    List<WkrjPermissionNew> getPermissionByModuleId(String moduleId);

    boolean delPermission(String id);

    List<WkrjPermissionNew> getPermissionList(@Param("offset") int offset, @Param("limit") int limit,
                                              @Param("module_id") String module_id);

    long getPermissionListCount(@Param("module_id") String module_id);

    boolean checkIsHavePermission(@Param("perm_flag") String perm_flag, @Param("perm_id") String perm_id);

    boolean addPermission(WkrjPermissionNew perm);

    boolean updatePermission(WkrjPermissionNew perm);

    List<WkrjPermissionNew> getPermissionByPermId(String perm_id);


}