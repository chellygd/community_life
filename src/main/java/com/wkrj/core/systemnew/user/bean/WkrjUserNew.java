package com.wkrj.core.systemnew.user.bean;

import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位模块表 对应数据库表：wkrj_sys_user
 *
 * @author ziro
 */
@ApiModel(description = "用户信息实体")
@Data
public class WkrjUserNew implements Serializable {
    private static final long serialVersionUID = 166899787453006677L;

    @ApiModelProperty(name = "user_id", value = "用户id，主键", required = false)
    private String userId;
    @ApiModelProperty(value = "岗位名称", required = false)
    private String stationId;
    private String deptId;// 部门名称
    private String deptName;// 部门名称
    private String userName;// 用户名
    private String userPassword;// 密码
    private String userPasswordOld;//原始密码
    //	private List<WkrjUserRole> user_role;// 角色
    private List<WkrjRoleNew> userRole;// 角色
    private String userRealname;// 真实姓名
    private String userCard;// 身份证
    private String userIsEnable;// 是否禁用0不禁用1禁用
    private String userInputtime;// 录入时间
    private String userLastIp;// 最后登录ip
    private String userNo;// 工号--仅用于学院OA
    private String userLastTime;// 最后登录时间
    private String userTel;// 电话
    private Integer userAccounttype;// 用户类型0是普通用户1是开发管理员用户
    private Integer userOrder;//排序
    private String userIsdel;//删除   默认0删除1
    private String userUrl;//用户头像

}
