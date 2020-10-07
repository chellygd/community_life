package com.wkrj.core.systemnew.department.controller;

import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.systemnew.department.bean.WkrjDeptNew;
import com.wkrj.core.systemnew.department.service.WkrjDeptNewService;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("wkrjsystemnew/wkrjDept")
public class WkrjDeptNewController {
	
	@Autowired
	private WkrjDeptNewService deptService;
	
	/**
	 * 获取部门树
	 * @return
	 */
	@RequestMapping("getDeptTree")
	@ResponseBody
	public LayuiJson getDeptTree(String dept_id,HttpServletRequest request){
		LayuiJson json = new LayuiJson();
		WkrjUserNew user=(WkrjUserNew) request.getSession().getAttribute("usernew");
		String dept="";
		if(user != null){
			if(user.getUserAccounttype()==1){
				dept="-1";
			}else{
				dept=user.getDeptId();
			}
			
		}
		Object roleList = deptService.getRoleList(dept,dept_id);
		json.setData(roleList);
		return json;
	}
	/**
	 * 组织机构列表
	 * @param request
	 * @return
	 */
	@RequestMapping("getDeptAndSchoolGridList")
	@ResponseBody
	public LayuiJson getDeptAndSchoolGridList(HttpServletRequest request){
		LayuiJson json = new LayuiJson();
		WkrjUserNew user=(WkrjUserNew) request.getSession().getAttribute("usernew");
		String dept="";
		boolean isGly = true;
			/*if ("2".equals()) {
				isGly = true;
				break;
		}*/
		
		if(user != null){
			if(user.getUserAccounttype()==1){
				dept="-1";
			}else{
				dept=user.getDeptId();
			}

		}
		//System.out.println(dept);
		List<Map<String,Object>> list=null;
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			list = deptService.getDeptAndSchoolGridList(dept,true,"");
			json.setData(list);
			//map.put("Rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return map;
		return json;
	}
	/**
	 * 组织机构--添加
	 * @param dept
	 * @return
	 */
	@RequestMapping("addDept")
	@ResponseBody
	public LayuiJson addDept(WkrjDeptNew dept,HttpServletRequest request){
		
		LayuiJson json = new LayuiJson();	
		WkrjUserNew user=(WkrjUserNew) request.getSession().getAttribute("usernew");
		if(user != null){
			if(StringUtils.isEmpty(dept.getDeptParentId())){
				if(user.getUserAccounttype()==1){
					dept.setDeptParentId("-1");
				}else{
					dept.setDeptParentId(user.getDeptId());
				}
			}
		}
		if(deptService.addDept(dept)){
			json.setSuccess(true);
			json.setMsg("保存成功");
		}
		
		return json;
	}
	/**
	 * 组织机构--修改
	 * @param dept
	 * @param dept_parent_id_old
	 * @return
	 */
	@RequestMapping("updateDept")
	@ResponseBody
	public LayuiJson updateDept(WkrjDeptNew dept,String dept_parent_id_old,HttpServletRequest request){
		
		LayuiJson json = new LayuiJson();
		WkrjUserNew user=(WkrjUserNew) request.getSession().getAttribute("usernew");
		if(user != null){
			if(StringUtils.isEmpty(dept.getDeptParentId())){
				if(user.getUserAccounttype()==1){
					dept.setDeptParentId("-1");
				}else{
					dept.setDeptParentId(user.getDeptId());
				}
			}
		}
		json =deptService.updateDept(dept,dept_parent_id_old);
		
		return json;
	}
	/**
	 * 组织机构--删除
	 * @param id
	 * @return
	 */
	@RequestMapping("delDept")
	@ResponseBody
	public LayuiJson delDept(String id){
		
		LayuiJson json = new LayuiJson();
		
		//首先判断是否还有孩子
		if(deptService.getDeptChildCount(id)<=0){
			if(deptService.delDept(id)){
				json.setSuccess(true);
				json.setMsg("删除成功");
			}
		}else{
			json.setMsg("请先删除子节点");
		}
		
		return json;
	}
	/**
	 * 获取部门树——懒加载
	 * @return
	 */
	@RequestMapping("getDeptTree_lazy")
	@ResponseBody
	public LayuiJson getDeptTree_lazy(String dept_id,HttpServletRequest request){
		LayuiJson json = new LayuiJson();
		WkrjUserNew user=(WkrjUserNew) request.getSession().getAttribute("usernew");
		if(StringUtils.isEmpty(dept_id)){
			if(user != null){
				if(user.getUserAccounttype()==1){
					dept_id="-1";
				}else{
					dept_id=user.getDeptId();
				}
			}
		}
		/*if(StringUtils.isEmpty(dept_id)||"null".equals(dept_id)){
			dept_id="-1";
		}*/
		List<Map<String,Object>> list = deptService.getDeptTree_lazy(dept_id);
		json.setData(list);
		return json;
	}
}
