package com.wkrj.core.systemnew.wkrjloginnew.service;

import com.wkrj.core.systemnew.user.bean.WkrjUserNew;

/**
 * 继承security的UserDetailsService
 */
public interface WkrjNewLonginService {

	String findUserByNameAndPwd(WkrjUserNew user);

	WkrjUserNew getUserInfoById(String userId);


}
