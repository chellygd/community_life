package com.wkrj.module.mpdemo.controller;

import com.wkrj.core.component.jwt.IgnoreToken;
import com.wkrj.core.utils.LayuiJson;
import com.wkrj.module.mpdemo.bean.MpDemo;
import com.wkrj.module.mpdemo.service.MpDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName MpDemoController
 * @Description mybatis-plus基础演示
 * @Author zhaoxiaohua
 * @Date 2020/6/10 10:55
 * @Version 1.0
 */
@Controller
@RequestMapping("mpDemoController")
public class MpDemoController {

    @Autowired
    private MpDemoService mpDemoService;

    /**
     * 模拟添加信息
     * @return
     */
    @RequestMapping("addInfo")
    @ResponseBody
    @IgnoreToken
    public LayuiJson addInfo(){

        LayuiJson json = new LayuiJson();

        MpDemo mp = new MpDemo();
        mp.setUserName("test");
        mp.setCommite(1);

        if(this.mpDemoService.addInfo(mp)){
            json.setMsg("添加成功");
            json.setSuccess(true);
        }


        return json;
    }

    /**
     * 模拟修改信息
     * @return
     */
    @RequestMapping("updateInfo")
    @ResponseBody
    @IgnoreToken
    public LayuiJson updateInfo(){

        LayuiJson json = new LayuiJson();

        MpDemo mp = new MpDemo();
        mp.setUserName("test2");
        mp.setCommite(1);
        mp.setId(34);

        if(this.mpDemoService.updateInfo(mp)){
            json.setMsg("修改成功");
            json.setSuccess(true);
        }


        return json;
    }

    /**
     * 模拟删除信息
     * @return
     */
    @RequestMapping("deleteInfo")
    @ResponseBody
    @IgnoreToken
    public LayuiJson deleteInfo(){

        LayuiJson json = new LayuiJson();

        MpDemo mp = new MpDemo();
        mp.setId(34);

        if(this.mpDemoService.deleteInfo(mp)){
            json.setMsg("删除成功");
            json.setSuccess(true);
        }


        return json;
    }

    /**
     * 模拟获取列表信息
     * @return
     */
    @RequestMapping("listInfo")
    @ResponseBody
    @IgnoreToken
    public LayuiJson listInfo(Integer page, Integer rows){

        LayuiJson json = new LayuiJson();

        MpDemo mp = new MpDemo();
        //mp.setId(34);
        return this.mpDemoService.listInfo(mp,page,rows);
    }

    /**
     * 自己写sql进行分页
     * */
    @RequestMapping("listInfoOther")
    @ResponseBody
    @IgnoreToken
    public LayuiJson listInfoOther(Integer page, Integer rows){

        return this.mpDemoService.listInfoOther(page,rows);
    }
}
