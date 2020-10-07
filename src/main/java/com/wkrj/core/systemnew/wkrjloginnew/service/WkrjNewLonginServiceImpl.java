package com.wkrj.core.systemnew.wkrjloginnew.service;

import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.systemnew.wkrjloginnew.dao.WkrjNewLoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wkrjNewLonginService")
public class WkrjNewLonginServiceImpl implements WkrjNewLonginService {

	@Autowired 
	private WkrjNewLoginDao wkrjNewLoginDao;
	
	@Override
	public String findUserByNameAndPwd(WkrjUserNew user) {
		return wkrjNewLoginDao.findUserByNameAndPwd(user);
	}

	@Override
	public WkrjUserNew getUserInfoById(String userId) {
		return wkrjNewLoginDao.getUserInfoById(userId);
	}
}
