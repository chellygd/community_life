package com.wkrj.core.systemnew.permission.controller;

import com.wkrj.core.utils.Guid;
import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.systemnew.permission.bean.WkrjPermissionNew;
import com.wkrj.core.systemnew.permission.service.WkrjPermissionNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("wkrjsystemnew/wkrjPermission")
public class WkrjPermissionNewController {

	@Autowired
	private WkrjPermissionNewService service;
	
	/**
	 * 权限列表
	 * @param limit
	 * @param page
	 * @param module_id
	 * @return
	 */
	@RequestMapping("getPermissionList")
	@ResponseBody
	public LayuiJson getPermissionList(int limit, int page,String module_id){
		LayuiJson json = new LayuiJson();
		int offset = (page-1)*limit;
		
		List<WkrjPermissionNew> list = this.service.getPermissionList(offset,limit, module_id);
		long count = this.service.getPermissionList(module_id);
		json.setData(list);
		json.setCount(String.valueOf(count));
		return json;
	}
	/**
	 * 添加权限
	 * @param perm
	 * @return
	 */
	@RequestMapping("addPermission")
	@ResponseBody
	public LayuiJson addPermission(WkrjPermissionNew perm){
		
		LayuiJson json = new LayuiJson();
		
		String id = Guid.getGuid();
		perm.setPermId(id);
		if(!service.checkIsHavePermission(perm.getPermFlag(),id)){
			
			if(service.addPermission(perm)){
				json.setSuccess(true);
				json.setMsg("保存成功");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("该权限已存在");
		}
		
		return json;
	}
	/**
	 * 修改权限
	 * @param module
	 * @return
	 */
	@RequestMapping("updatePermission")
	@ResponseBody
	public LayuiJson updatePermission(WkrjPermissionNew module){
		
		LayuiJson json = new LayuiJson();
		
		if(!service.checkIsHavePermission(module.getPermFlag(),module.getPermId())){
		
			if(service.updatePermission(module)){
				json.setSuccess(true);
				json.setMsg("修改成功");
			}
		}else{
			json.setSuccess(false);
			json.setMsg("该权限已存在");
		}
		return json;
	}
	/**
	 * 权限删除
	 * @param id
	 * @param icon
	 * @return
	 */
	@RequestMapping("delPermission")
	@ResponseBody
	public LayuiJson delPermission(String id){
		LayuiJson json = new LayuiJson();
		boolean d = service.delPermission(id);
		if(d){
			json.setMsg("保存成功！");
			json.setSuccess(true);
		}else{
			json.setMsg("保存失败！");
		}
		return json;
	}
}
