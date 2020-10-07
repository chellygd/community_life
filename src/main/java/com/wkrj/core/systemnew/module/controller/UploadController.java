package com.wkrj.core.systemnew.module.controller;

import com.wkrj.core.utils.FileUtils;
import com.wkrj.core.utils.LayuiJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author CXR
 * @version 创建时间：2019年4月19日 下午2:25:15
 * 功能：上传
 */
@Controller
@RequestMapping("wkrjsystemnew/upload")
public class UploadController {

	@RequestMapping("uploadModule")
	@ResponseBody
	public LayuiJson getGridInfo_tree(HttpServletRequest request) {
		
		LayuiJson json = new LayuiJson();
		String dirname="Module";
		List<Map<String, String>> lkh_uploadFile = FileUtils.wkrj_uploadFile(request, dirname);
		json.setSuccess(true);
		json.setData(lkh_uploadFile);
		return json;
		
	}
}
