package com.wkrj.core.systemnew.authority.controller;

import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author CXR
 * 
 */
@Controller
@RequestMapping("wkrjsystem/wkrjAuthority")
public class WkrjAuthorityController {
	
	/**
	 * 按钮权限判断
	 * @param perm 按钮的ekper值
	 * @param request
	 * @return
	 */
	@RequestMapping("/judgeAuthority")
	@ResponseBody
	public Object judgeAuthority(String perm,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		LayuiJson json = new LayuiJson();
		WkrjUserNew user = (WkrjUserNew) request.getSession().getAttribute("usernew");
		String perms = request.getSession().getAttribute("userPermission")+"";
		map.put("Authority", "1");//不存在权限
		if(user !=null){
			//管理员
			if(1 == user.getUserAccounttype()){
				map.put("Authority", "2");//存在权限
				json.setData(map);
				return json;
			}
			if(!"null".equals(perms)&&!StringUtils.isEmpty(perms)){
				if(perms.indexOf(perm)>=0){
					map.put("Authority", "2");//存在权限
				}
			}
		}else{
			map.put("Authority", "3");//不存在session
		}
		json.setData(map);
		return json;
	}
}
