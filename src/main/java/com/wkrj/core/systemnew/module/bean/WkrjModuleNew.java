package com.wkrj.core.systemnew.module.bean;

import com.wkrj.core.systemnew.menu.bean.WkrjMenuNew;
import lombok.Data;

import java.util.List;

/**
 * 功能模块表
 * 对应数据库表：wkrj_sys_module
 *
 * @author ziro
 */
@Data
public class WkrjModuleNew {

    private String moduleId;
    private String moduleName;// 模块名称
    private boolean moduleLevel;// 模块等级，是否为叶子节点0不是1是
    private String moduleUrl;// 模块url
    private String moduleParentId;// 父模块ID
    private int moduleOrder;// 模块顺序（1、2、3、4。。。。）
    private String moduleIcon;// 模块图标
    private boolean moduleIsDisplay;// 模块是否显示
    private String moduleOther;// 模块备注
    private List<WkrjMenuNew> menu;//菜单
    private String moduleIconNew;
}
