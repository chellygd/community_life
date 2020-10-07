package com.wkrj.core.systemnew.department.bean;

import lombok.Data;

/**
 * 功能模块表 对应数据库表：wkrj_sys_department
 *
 * @author ziro
 */
@Data
public class WkrjDeptNew {

    private String deptId;
    private String deptOrder;
    private String deptName;
    private String deptParentId;
    private String deptIsLeaf;
    private String deptOther;
    private String pdeptName;
    private String deptIsdel;
}
