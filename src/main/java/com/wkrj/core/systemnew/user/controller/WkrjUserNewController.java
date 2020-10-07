package com.wkrj.core.systemnew.user.controller;

import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.utils.MD5;
import com.wkrj.core.systemnew.department.service.WkrjDeptNewService;
import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.systemnew.user.service.WkrjUserNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ziro
 * @date 2019年8月1日
 */
@Api(tags = "WkrjUserNewController", value = "用户管理", description = "用户管理")
@Controller
@RequestMapping("wkrjsystemnew/wkrjUser")
public class WkrjUserNewController {

    @Autowired
    private WkrjUserNewService userService;

    @Autowired
    private WkrjDeptNewService deptService;


    @ApiOperation(value = "获取用户列表", notes = "分页获取用户列表", httpMethod = "GET")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "limit", value = "每页行数", paramType="query", required = false, dataType = "Integer"),
    	@ApiImplicitParam(name = "page", value = "页码", paramType="query", required = false, dataType = "Integer"),
    	@ApiImplicitParam(name = "deptId", value = "部门id", paramType="query", required = false, dataType = "String"),
        @ApiImplicitParam(name = "userName", value = "用户名", paramType="query", required = false, dataType = "String"),
    })
    @RequestMapping("getUserList")
    @ResponseBody
    public LayuiJson getUserList(int limit, int page, String deptId, HttpServletRequest request) {
        LayuiJson json = new LayuiJson();
        String userName = request.getParameter("userName");//
        WkrjUserNew user = (WkrjUserNew) request.getSession().getAttribute("usernew");
        String dept_id = "";//登录人的部门
        if (user != null) {
            if (user.getUserAccounttype() == 1) {
                dept_id = "-1";
            } else {
                dept_id = user.getDeptId();
            }
        }

        int offset = (page - 1) * limit;

        List<WkrjUserNew> list = this.userService.getUserList(offset, limit, deptId, userName, dept_id);
        long count = this.userService.getUserList(deptId, userName, dept_id);

        for (WkrjUserNew useri : list) {
            //获取角色信息
            List<WkrjRoleNew> roleListByUserId = this.userService.getRoleListByUserId(useri.getUserId());
            useri.setUserRole(roleListByUserId);

        }
        json.setData(list);
        json.setCount(String.valueOf(count));
        return json;
    }

    @ApiOperation(value = "获取角色列表", notes = "", httpMethod = "GET")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "request", value = "httpRequest", required = false)
    })
    @RequestMapping("getRoleTree")
    @ResponseBody
    public LayuiJson getRoleTree(HttpServletRequest request) {
        LayuiJson json = new LayuiJson();
        List<Map<String, Object>> list = userService.getRoleList();
        json.setData(list);
        return json;
    }

    @ApiOperation(value = "添加用户", notes = "用户名不能重复")//httpMethod = "POST"
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "user_id", value = "用户id", paramType="query", required = true, dataType = "String"),
    	@ApiImplicitParam(name = "roleIds", value = "角色字符串，用逗号连接", paramType="query", required = false, dataType = "String")
    })
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public LayuiJson addUser(WkrjUserNew wkrjUserNew, String role) {
        LayuiJson json = new LayuiJson();
        String res = userService.addUser(wkrjUserNew, role);
        if ("0".equals(res)) {
            json.setSuccess(true);
            json.setMsg("保存成功");
        } else if ("1".equals(res)) {
            json.setMsg("用户名已经存在");
        }
        return json;
    }

    @ApiOperation(value = "用户修改", notes = "用户修改", httpMethod = "GET")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "role", value = "角色ID", paramType="query", required = true, dataType = "String")
    })
    @RequestMapping("updateUser")
    @ResponseBody
    public LayuiJson updateUser(WkrjUserNew module, String role) {

        LayuiJson json = new LayuiJson();

        String res = userService.updateUser(module, role);

        if ("0".equals(res)) {
            json.setSuccess(true);
            json.setMsg("修改成功");
        } else if ("1".equals(res)) {
            json.setMsg("用户名已经存在");
        }

        return json;
    }

    /**
     * 删除用户，删除前需要先删除绑定的菜单和权限
     *
     * @param id
     * @return
     */
    @RequestMapping("delUser")
    @ResponseBody
    public LayuiJson delUser(String id) {

        LayuiJson json = new LayuiJson();

        if (null != id && !"".equals(id)) {

            String[] id_ = id.split(",");

            for (int i = 0; i < id_.length; i++) {

                if (userService.delUser(id_[i])) {
                    json.setSuccess(true);
                    json.setMsg("删除成功");
                }
            }
        }

        return json;
    }

    /**
     * 禁用、启用
     *
     * @param id
     * @param flag
     * @return
     */
    @RequestMapping("forbiddenUser")
    @ResponseBody
    public LayuiJson forbiddenUser(String id, String flag) {

        LayuiJson json = new LayuiJson();

        if (userService.forbiddenUser(id, flag)) {
            json.setSuccess(true);
            json.setMsg("操作成功");
        }

        return json;
    }

    /**
     * 重置密码123
     *
     * @param id
     * @return
     */
    @RequestMapping("repeatPw")
    @ResponseBody
    public LayuiJson repeatPw(String id) {

        LayuiJson json = new LayuiJson();

        if (this.userService.repeatPw(id, "123")) {
            json.setSuccess(true);
            json.setMsg("重置成功");
        }

        return json;
    }

    /**
     * 获取部门树
     *
     * @return
     */
    @RequestMapping("getDeptTree")
    @ResponseBody
    public LayuiJson getDeptTree(String dept_id, HttpServletRequest request) {
        LayuiJson json = new LayuiJson();
        WkrjUserNew user = (WkrjUserNew) request.getSession().getAttribute("usernew");
        String dept = "";
        if (user != null) {
            if (user.getUserAccounttype() == 1) {
                dept = "-1";
            } else {
                dept = user.getDeptId();
            }

        }
        Object roleList = deptService.getRoleList(dept, dept_id);
        json.setData(roleList);
        return json;
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("updatePwd")
    @ResponseBody
    public LayuiJson updatePwd(String oldPassword, String password, HttpServletRequest request) {
        WkrjUserNew user = (WkrjUserNew) request.getSession().getAttribute("usernew");
        String userId = user.getUserId();

        LayuiJson json = new LayuiJson();

        if (MD5.MD5Encode(oldPassword).equals(user.getUserPassword())) {

            if (userService.updatePassword(userId, MD5.MD5Encode(password))) {

                json.setMsg("修改成功");
                json.setSuccess(true);

                return json;
            }
        } else {

            json.setMsg("原密码不正确");
            json.setSuccess(false);

            return json;
        }

        return json;
    }

    //获取用户信息
    @RequestMapping("getUserInfo")
    @ResponseBody
    public LayuiJson getUserInfo(HttpServletRequest request) {
        LayuiJson json = new LayuiJson();
        WkrjUserNew user = (WkrjUserNew) request.getSession().getAttribute("usernew");
        if (user != null) {
            String user_id = user.getUserId() + "";
            WkrjUserNew userbyid = userService.getUserInfoById(user_id);
			/*List<WkrjRoleNew> roleListByUserId = this.userService.getRoleListByUserId(user_id);
			userbyid.setUserRole(roleListByUserId);*/
            json.setSuccess(true);
            json.setData(userbyid);
        } else {
            json.setCode(1001);
            json.setSuccess(false);
            json.setMsg("用户登录信息失效，请重新登录！");
        }

        return json;
    }

    @RequestMapping("updateUserInfo")
    @ResponseBody
    public LayuiJson updateUserInfo(WkrjUserNew module) {

        LayuiJson json = new LayuiJson();

        boolean d = userService.updateUserInfo(module);

        if (d) {
            json.setSuccess(true);
            json.setMsg("修改成功");
        } else {
            json.setMsg("修改失败");
        }

        return json;
    }
}
