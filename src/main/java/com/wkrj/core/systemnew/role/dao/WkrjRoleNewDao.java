package com.wkrj.core.systemnew.role.dao;

import com.wkrj.core.systemnew.permission.bean.WkrjPermissionNew;
import com.wkrj.core.systemnew.role.bean.WkrjRoleMenuNew;
import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.role.bean.WkrjRolePermissionNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface WkrjRoleNewDao {

    List<WkrjRoleNew> getRoleList(@Param("offset") int offset, @Param("page") int page,
                                  @Param("role_dept") String dept_id);

    long getRoleListCount(@Param("role_dept") String dept_id);

    boolean addRole(WkrjRoleNew module);

    boolean updateRole(WkrjRoleNew module);

    List<WkrjRoleMenuNew> getRoleMenuByRoleId(@Param("role_id") String role_id);

    boolean delRoleMenu(@Param("role_id") String role_id);

    boolean delRole(@Param("role_id") String role_id);

    List<WkrjRolePermissionNew> getRolePermissionByRoleId(@Param("role_id") String role_id);

    boolean delRolePermission(@Param("role_id") String role_id);

    WkrjRoleNew getRoleById(String role_id);

    boolean saveRoleMenu(WkrjRoleMenuNew rm);

    boolean saveRolePermission(WkrjRolePermissionNew rp);

    boolean delRoleNew(@Param("role_id") String role_id, @Param("role_isdel") String role_isdel);

    List<WkrjRoleMenuNew> getMenuByRoleIds(@Param("role_id") String ids[]);

    List<WkrjPermissionNew> getAllPermission();

    /**
     * 查询全部角色和授权信息
     *
     * @return
     */
    List<Map<String, Object>> getAllRolePermission();

}
