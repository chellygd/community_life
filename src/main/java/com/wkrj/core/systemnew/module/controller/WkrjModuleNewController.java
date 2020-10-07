package com.wkrj.core.systemnew.module.controller;

import com.wkrj.core.component.log.WkrjLogInfo;
import com.wkrj.core.utils.Guid;
import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.systemnew.module.bean.WkrjModuleNew;
import com.wkrj.core.systemnew.module.service.WkrjModuleNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 模块管理
 * @author cxr
 *
 */
@Controller
@RequestMapping("wkrjsystemnew/wkrjModule")
public class WkrjModuleNewController {
	
	@Autowired
	private WkrjModuleNewService moduleNewService;
	
	/**
	 * 模块管理列表
	 * @param request
	 * @return
	 */
	@RequestMapping("getGridInfo")
	@ResponseBody
	public LayuiJson getGridInfo(HttpServletRequest request) {
		
		LayuiJson json = new LayuiJson();
		List<Map<String,Object>> list = moduleNewService.getGridInfo("-1");
		json.setSuccess(true);
		json.setData(list);
		return json;
		
	}
	/**
	 * 模块下拉树
	 * @param request
	 * @return
	 */
	@RequestMapping("getGridInfo_tree")
	@ResponseBody
	public LayuiJson getGridInfo_tree(HttpServletRequest request,String module_id) {
		
		LayuiJson json = new LayuiJson();
		List<Map<String,Object>> list = moduleNewService.getGridInfo_tree("-1",module_id);
		json.setSuccess(true);
		json.setData(list);
		return json;
		
	}
	/**
	 * 模块添加
	 * @param module
	 * @return
	 */
	@RequestMapping("addModule")
	@ResponseBody
	@WkrjLogInfo(logmethod = "wkrjsystemnew/wkrjModule/addModule", logmsg = "添加模块")
	public LayuiJson addModule(WkrjModuleNew module){
		
		LayuiJson json = new LayuiJson();
		json.setMsg("保存失败！");
		module.setModuleId(Guid.getGuid());
		if(false==module.isModuleLevel() && (null==module.getModuleParentId() || "".equals(module.getModuleParentId()))){
			module.setModuleParentId("-1");
		}
		
		if(moduleNewService.addModule(module)){
			json.setSuccess(true);
			json.setMsg("保存成功！");
		}
		
		return json;
	}
	/**
	 * 模块修改
	 * @param module
	 * @return
	 */
	@RequestMapping("updateModule")
	@ResponseBody
	public LayuiJson updateModule(WkrjModuleNew module){
		
		LayuiJson json = new LayuiJson();
		if(null==module.getModuleParentId() || "".equals(module.getModuleParentId())){
			module.setModuleParentId("-1");
		}
		if(moduleNewService.updateModule(module)){
			json.setSuccess(true);
			json.setMsg("修改成功");
		}
		
		return json;
	}
	/**
	 * 模块删除
	 * @param id
	 * @param 
	 * @return
	 */
	@RequestMapping("delModule")
	@ResponseBody
	public LayuiJson delModule(String id){
		
		LayuiJson json = new LayuiJson();
		
		int count = this.moduleNewService.checkIsHaveChildren(id);
		if(count>0){
			json.setSuccess(false);
			json.setMsg("请先删除子节点");
		}else{
			try{
				if(moduleNewService.delModule(id)){
					json.setSuccess(true);
					json.setMsg("删除成功");
				}
			}catch(Exception e){
				if(e.getStackTrace()[0].getClassName().indexOf("SQLErrorCodeSQLExceptionTranslator")>=0){
					json.setMsg("删除失败,已被其他模块使用");
				}else{
					e.printStackTrace();
				}
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
		List<Map<String,Object>> list=this.moduleNewService.getIcon();
		json.setData(list);
		return json;
	}
}
