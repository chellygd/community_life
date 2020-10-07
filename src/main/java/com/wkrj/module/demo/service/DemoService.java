package com.wkrj.module.demo.service;


import com.wkrj.module.demo.bean.Demo;

import java.util.List;
import java.util.Map;

/**
 * 测试服务接口
 * @author ziro
 */
public interface DemoService {

    /**
     * 查询文件列表
     * @return
     */
    List<Map<String, Object>> listFile();

    List<Demo> test();
}
