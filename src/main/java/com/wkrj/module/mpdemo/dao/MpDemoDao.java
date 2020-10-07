package com.wkrj.module.mpdemo.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wkrj.module.mpdemo.bean.MpDemo;

import java.util.Map;

/**
 * @ClassName MpDemoDao
 * @Description TODO
 * @Author zhaoxiaohua
 * @Date 2020/6/10 10:39
 * @Version 1.0
 */
public interface MpDemoDao extends BaseMapper<MpDemo> {

    /** 自己写sql分页*/
    public IPage<Map> listInfoOther(Page page);

}
