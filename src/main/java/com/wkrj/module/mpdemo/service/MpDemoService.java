package com.wkrj.module.mpdemo.service;


import com.wkrj.core.utils.LayuiJson;
import com.wkrj.module.mpdemo.bean.MpDemo;

/**
 * 测试服务接口
 * @author zhaoxiaohua
 */
public interface MpDemoService {

    /** 添加*/
    public boolean addInfo(MpDemo mp);
    /** 修改*/
    public boolean updateInfo(MpDemo mp);
    /** 删除*/
    public boolean deleteInfo(MpDemo mp);
    /** 获取列表内容*/
    public LayuiJson listInfo(MpDemo mp, Integer page, Integer rows) ;
    /** 自己写sql进行分页*/
    public LayuiJson listInfoOther(Integer page, Integer rows) ;

}
