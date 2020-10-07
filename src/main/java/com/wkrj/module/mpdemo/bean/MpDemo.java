package com.wkrj.module.mpdemo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @ClassName MpDemo
 * @Description TODO
 * @Author zhaoxiaohua
 * @Date 2020/6/10 10:39
 * @Version 1.0
 */
@TableName("z_wkrj_operate_log")
public class MpDemo implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * 主键
     * @TableId中可以决定主键的类型,不写会采取默认值,默认值可以在yml中配置
     * AUTO: 数据库ID自增
     * INPUT: 用户输入ID
     * ID_WORKER: 全局唯一ID，Long类型的主键
     * ID_WORKER_STR: 字符串全局唯一ID
     * UUID: 全局唯一ID，UUID类型的主键
     * NONE: 该类型为未设置主键类型
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_name")
    private String userName;

    private int commite;


    public int getCommite() {
        return commite;
    }

    public void setCommite(int commite) {
        this.commite = commite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
