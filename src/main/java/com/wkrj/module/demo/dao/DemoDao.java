package com.wkrj.module.demo.dao;


import com.wkrj.module.demo.bean.Demo;

import java.util.List;
import java.util.Map;

/**
 * @author ziro
 */
public interface DemoDao {
    /**
     * 查询测试文件列表
     * @return
     */
    List<Map<String, Object>> listDemo();

    List<Demo> test();

}
