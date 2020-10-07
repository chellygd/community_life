package com.wkrj.module.mpdemo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wkrj.core.utils.LayuiJson;
import com.wkrj.module.mpdemo.bean.MpDemo;
import com.wkrj.module.mpdemo.dao.MpDemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试服务接口实现
 *
 * @author zhaoxiaohua
 */
@Service("mpDemoService")
public class MpDemoServiceImpl implements MpDemoService {

    @Autowired
    private MpDemoDao mpDemoDao;

    @Override
    public boolean addInfo(MpDemo mp) {

        int num = mpDemoDao.insert(mp);
        return (num > 0 ? true : false);
    }

    @Override
    public boolean updateInfo(MpDemo mp) {

        int num = mpDemoDao.updateById(mp);
        return (num > 0 ? true : false);
    }

    @Override
    public boolean deleteInfo(MpDemo mp) {

        int num = mpDemoDao.deleteById(mp.getId());
        return (num > 0 ? true : false);
    }

    @Override
	public LayuiJson listInfo(MpDemo mp,Integer page, Integer rows) {


		LayuiJson json = new LayuiJson();

        IPage<MpDemo> p = new Page<>(page,rows);
        QueryWrapper wrapper = new QueryWrapper<>();
        //查询显示的列名
        wrapper.select("commite,user_name as userName,id");
        //进行查询
        wrapper.eq("user_name","wkrj");

        IPage iPage = mpDemoDao.selectPage(p, wrapper);
        json.setData(iPage.getRecords());
        json.setCount(iPage.getTotal()+"");

        return json;
	}

    @Override
	public LayuiJson listInfoOther(Integer page, Integer rows) {


		LayuiJson json = new LayuiJson();

        IPage iPage = mpDemoDao.listInfoOther(new Page<>(page, rows));

        json.setData(iPage.getRecords());
        json.setCount(iPage.getTotal()+"");

        return json;
	}


}
