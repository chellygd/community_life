package com.wkrj.core.systemnew.role.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于管理角色和权限表的中间表
 *
 * @author ziro
 */
@Data
public class WkrjRolePermissionNew implements Serializable {
    private static final long serialVersionUID = 8296025958343302509L;

    private String permId;// 权限id
    private String roleId;// 角色id

}
