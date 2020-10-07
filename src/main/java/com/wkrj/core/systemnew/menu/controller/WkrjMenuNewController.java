package com.wkrj.core.systemnew.menu.controller;

import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.systemnew.menu.service.WkrjMenuNewService;
import com.wkrj.core.systemnew.module.bean.WkrjModuleNew;
import com.wkrj.core.systemnew.module.service.WkrjModuleNewService;
import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("wkrjsystemnew/wkrjMenu")
public class WkrjMenuNewController {
	
	@Autowired
	private WkrjMenuNewService menuService;
	@Autowired
	private WkrjModuleNewService moduleService;
	
	
	/**
	 * 左侧路由树
	 * @param request
	 * @return
	 */
	@RequestMapping("getMenu")
	@ResponseBody
	public LayuiJson getMenu(HttpServletRequest request) {
		LayuiJson json = new LayuiJson();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//
		WkrjUserNew user=(WkrjUserNew) request.getSession().getAttribute("usernew");
		if(user != null){
			if(user.getUserAccounttype()==1){
				//管理员、开发人员
				list = moduleService.getGridInfo_gly("-1");
			}else{
				//普通用户
				
				List<WkrjRoleNew> user_role = user.getUserRole();
				String roleIds = "";
				if(user_role.size()>0){
					
					for(WkrjRoleNew role:user_role){
						
						roleIds += role.getRoleId();
						roleIds +=","; 
					}
					
					if(null!=roleIds && !"".equals(roleIds)){
						
						list = menuService.getLeftMenuByRole("-1",roleIds);
					}
				}
			}
			
		}
		
		json.setData(list);
		return json;
	}
	
	
	
	
	/**
	 * 菜单管理:获取左侧菜单的列表
	 * @param request
	 * @return
	 */
	@RequestMapping("getTreeInfo")
	@ResponseBody
	public LayuiJson getTreeInfo(HttpServletRequest request) {
		LayuiJson json = new LayuiJson();
		List<Map<String,Object>> list = menuService.getGridInfo("-1");
		json.setData(list);
		return json;
	}
	/**
	 * 获取右侧模块菜单的列表
	 * @param request
	 * @return
	 */
	@RequestMapping("getGridInfo")
	@ResponseBody
	public LayuiJson getGridInfo(HttpServletRequest request) {
		LayuiJson json = new LayuiJson();
		List<Map<String,Object>> list = moduleService.getDisplayGridInfo("-1");
		json.setData(list);
		return json;
		
	}
	/**
	 * 添加显示菜单
	 * @param module
	 * @param module_view
	 * @return
	 */
	@RequestMapping("addMenu")
	@ResponseBody
	public LayuiJson addMenu(WkrjModuleNew module, String module_view){
		
		LayuiJson json = new LayuiJson();
		
		if("".equals(module.getMenu().get(0).getMenuParentId())){
			module.getMenu().get(0).setMenuParentId("-1");
		}
		
		module.setModuleUrl(module_view);
		if(menuService.addMenu(module)){
			json.setSuccess(true);
			json.setMsg("添加成功");
		}
		
		return json;
	}
	/**
	 * 修改显示菜单
	 * @param module
	 * @param yFileName
	 * @param module_view
	 * @return
	 */
	@RequestMapping("updateMenu")
	@ResponseBody
	public LayuiJson updateModule(WkrjModuleNew module,String yFileName,String module_view){
		
		LayuiJson json = new LayuiJson();
		
		module.setModuleUrl(module_view);
		if(menuService.updateMenu(module, yFileName)){
			json.setSuccess(true);
			json.setMsg("修改成功");
		}
		
		return json;
	}
	/**
	 * 删除显示菜单
	 * @param id
	 * @param icon
	 * @return
	 */
	@RequestMapping("delMenu")
	@ResponseBody
	public LayuiJson delMenu(String id,String icon){
		icon="";
		LayuiJson json = new LayuiJson();
		
		int count = this.menuService.checkIsHaveChildren(id);
		if(count>0){
			json.setSuccess(false);
			json.setMsg("请先删除子节点");
		}else{
			
			if(menuService.delMenu(id,icon)){
				json.setSuccess(true);
				json.setMsg("删除成功");
			}
		}
		
		return json;
	}
	/**
	 * 图标
	 * @return
	 */
	@RequestMapping("getIcon")
	@ResponseBody
	public LayuiJson getIcon(){
		
		LayuiJson json = new LayuiJson();
		List<Map<String,Object>> list=this.moduleService.getIcon();
		json.setData(list);
		return json;
	}
}
