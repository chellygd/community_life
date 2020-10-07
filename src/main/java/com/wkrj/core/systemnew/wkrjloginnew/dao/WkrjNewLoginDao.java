package com.wkrj.core.systemnew.wkrjloginnew.dao;

import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ziro
 */
public interface WkrjNewLoginDao {

    String findUserByNameAndPwd(WkrjUserNew user);

    WkrjUserNew getUserInfoById(@Param("userId") String userId);

}
