package com.wkrj.core.systemnew.role.controller;

import com.wkrj.core.utils.Guid;
import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.role.service.WkrjRoleNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("wkrjsystemnew/wkrjRole")
public class WkrjRoleNewController {
	
	@Autowired
	private WkrjRoleNewService roleService;
	/**
	 * 角色列表
	 * @param limit
	 * @param page
	 * @param dept_id
	 * @return
	 */
	@RequestMapping("getRoleList")
	@ResponseBody
	public LayuiJson getRoleList(int limit, int page,String dept_id){
		LayuiJson json = new LayuiJson();
		int offset = (page-1)*limit;
		
		List<WkrjRoleNew> list = this.roleService.getRoleList(offset,limit, dept_id);
		long count = this.roleService.getRoleList(dept_id);
		json.setData(list);
		json.setCount(String.valueOf(count));
		return json;
	}
	/**
	 * 
	 * @param perm
	 * @return
	 */
	@RequestMapping("addRole")
	@ResponseBody
	public LayuiJson addRole(WkrjRoleNew perm){
		
		LayuiJson json = new LayuiJson();
		
		perm.setRoleId(Guid.getGuid());
		perm.setRoleType("2");
		
		if(roleService.addRole(perm)){
			json.setSuccess(true);
			json.setMsg("保存成功");
		}
		
		return json;
	}
	/**
	 * 角色修改
	 * @param module
	 * @return
	 */
	@RequestMapping("updateRole")
	@ResponseBody
	public LayuiJson updateRole(WkrjRoleNew module){
		
		LayuiJson json = new LayuiJson();
		
		module.setRoleType("2");
		if(roleService.updateRole(module)){
			json.setSuccess(true);
			json.setMsg("修改成功");
		}
		
		return json;
	}
	
	/**
	 * 删除角色，删除前需要先删除绑定的菜单和权限
	 * @param id
	 * @return
	 */
	@RequestMapping("delRole")
	@ResponseBody
	public LayuiJson delRole(String id){
		
		LayuiJson json = new LayuiJson();
		
		if(null!=id && !"".equals(id)){
			
			String[] id_ = id.split(",");
			
			for(int i=0;i<id_.length;i++){
				
				if(roleService.delRole(id_[i])){
					json.setSuccess(true);
					json.setMsg("删除成功");
				}
			}
		}
		
		return json;
	}
	/**
	 * 复制角色
	 * @param role_id
	 * @return
	 */
	@RequestMapping("copyRole")
	@ResponseBody
	public LayuiJson copyRole(String role_id){
		
		LayuiJson json = new LayuiJson();
		json.setMsg("复制失败");
		
		if (roleService.copyRole(role_id)) {
			
			json.setMsg("复制成功");
			json.setSuccess(true);
			
		}
		
		return json;
	}
	/**
	 * 获取所有的菜单
	 * @return
	 */
	@RequestMapping("getMenuPermission")
	@ResponseBody
	public LayuiJson getMenuPermission1(String role_id,String parentId){
		LayuiJson json = new LayuiJson();
		if(null==parentId || "".equals(parentId)){
			parentId="-1";
		}
		Object menuPermission = roleService.getMenuPermission(role_id,parentId);
		json.setData(menuPermission);
		return json;
	}
	/**
	 * 保存权限
	 * @param menulist
	 * @param permissionlist
	 * @param rolerId
	 * @return
	 */
	@RequestMapping("setMenuPermission")
	@ResponseBody
    public LayuiJson setMenuPermission(String rolerId,String perm_ids,String menu_ids,String menu_order){
		
		LayuiJson json = new LayuiJson();
		json.setMsg("保存失败");
		
		if (roleService.setMenuPermission(menu_ids,perm_ids,rolerId,menu_order)) {
			
			json.setMsg("保存成功");
			json.setSuccess(true);
			
		}
	   return json;
   }
}
