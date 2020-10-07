package com.wkrj.core.systemnew.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.systemnew.user.bean.WkrjUserRoleNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface WkrjUserNewDao extends BaseMapper<WkrjUserNew> {

    List<WkrjUserNew> getUserList(@Param("offset") int offset, @Param("limit") int limit,
                                  @Param("deptId") String deptId,
                                  @Param("userName") String userName, @Param("dept_id") String dept_id);

    long getUserListCount(@Param("deptId") String deptId, @Param("userName") String userName
            , @Param("dept_id") String dept_id);

    List<WkrjRoleNew> getRoleListByUserId(@Param("user_id") String user_id);

    List<WkrjRoleNew> getRoleTree();

    long checkIsHaveNameOrGhOrSfz(@Param("name") String name,
                                  @Param("userId") String userId);

    boolean addUserRole(WkrjUserRoleNew ur);

    boolean addUser(WkrjUserNew module);

    boolean delUerRoleById(String id);

    boolean updateUser(WkrjUserNew module);

    boolean delUser_new(String user_id);

    boolean forbiddenUser(@Param("user_id") String id, @Param("user_is_enable") String flag);

    boolean repeatPw(@Param("user_id") String id, @Param("user_password") String pw);

    boolean updatePassword(@Param("id") String id, @Param("pw") String pw);

    WkrjUserNew getUserInfoById(@Param("user_id") String user_id);

    boolean updateUserInfo(WkrjUserNew module);

    WkrjUserNew findUserInfoByName(@Param("userName") String userName);

    WkrjUserNew findUserInfoByNameandPw(@Param("userName") String userName, @Param("pw") String pw);

}
