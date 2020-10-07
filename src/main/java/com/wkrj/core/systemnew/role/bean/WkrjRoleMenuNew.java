package com.wkrj.core.systemnew.role.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于管理角色和菜单表的中间表
 *
 * @author ziro
 */
@Data
public class WkrjRoleMenuNew implements Serializable {
    private static final long serialVersionUID = -2166363106613028448L;
    // 菜单id
    private String menuId;
    // 角色id
    private String roleId;
    // 菜单顺序
    private String menuOrder;

}
