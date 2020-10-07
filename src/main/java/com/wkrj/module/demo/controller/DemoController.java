package com.wkrj.module.demo.controller;

import cn.hutool.json.JSONUtil;
import com.wkrj.core.component.log.WkrjLogInfo;
import com.wkrj.core.configuration.security.SecurityUserDetailsService;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.utils.LayuiJson;
import com.wkrj.module.demo.service.DemoService;
import io.swagger.annotations.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;
import java.util.Map;


/**
 * 测试
 * @author ziro
 * @date 2019年5月28日
 */
@Controller
@RequestMapping("demo/demo")
@Api(tags = "测试接口Controller", value = "DemoController")
public class DemoController {
	
	@Autowired
	private DemoService demoService;
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;

	/**
	 * {"theme":{"color":{"main":"#20222A","selected":"#009688","alias":"default","index":0}},"access_token":""}
	 * 查询文件列表
	 * @return
	 */
	@RequestMapping("listFile")
	@ResponseBody
	@ApiOperation(value = "查询xx列表", notes = "备注说明：xxx")
	@ApiResponses({
			@ApiResponse(code = 400, message = "demoApiResponse")
	})
	@ApiImplicitParam(paramType="query", name = "userId",value = "用户ID", required = false, dataType = "String")
	@WkrjLogInfo(logmethod = "demo/demo/listFile", logmsg = "查询信息列表")
	public LayuiJson listFile(String userId){
		LayuiJson json = new LayuiJson();
		try {
			System.out.println("sxxxx");
			// mq发消息
			/*for (int i = 0; i < 10; i++) {
				amqpTemplate.convertAndSend("wkrjtestKey", "wkrjTestValue" + i);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		json.setSuccess(true);
		json.setMsg("成功");
		json.setData(demoService.listFile());
		return json;
		//return null;
	}

	@RequestMapping("test")
	@ResponseBody
	public Object test(){
		return demoService.test();
	}
	
	
}
