package com.wkrj.module.demo.service;

import com.wkrj.module.demo.bean.Demo;
import com.wkrj.module.demo.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 测试服务接口实现
 * @author ziro
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {
	
	@Autowired
	private DemoDao demoDao;

	@Override
	@Transactional
	public List<Map<String, Object>> listFile() {
		return demoDao.listDemo();
	}

	@Override
	public List<Demo> test() {
		return demoDao.test();
	}
}
