package com.wkrj.core.systemnew.permission.bean;

import lombok.Data;

/**
 * 权限
 *
 * @author ziro
 */
@Data
public class WkrjPermissionNew {

    private String permId;// 权限ID
    private String moduleId;// 模块ID
    private String permName;// 权限名称
    private String permFlag;// 权限标记
    private String permIcon;// 权限图标

}
