package com.wkrj.core.systemnew.menu.bean;

import lombok.Data;

/**
 * 功能模块表 对应数据库表：wkrj_sys_menu
 *
 * @author ziro
 */
@Data
public class WkrjMenuNew {

    private String menuId;
    private String moduleId;
    private String menuName;// 模块名称
    private boolean menuLevel;// 模块等级，是否为叶子节点0不是1是
    private String menuUrl;// 模块url
    private String menuParentId;// 父模块ID
    private String pmenuName;
    private int menuOrder;// 模块顺序（1、2、3、4。。。。）
    private String menuIcon;// 模块图标
    private boolean menuIsDisplay;// 模块是否显示
    private String menuOther;// 模块备注
    private String menuIconNew;

}
