package com.wkrj.core.systemnew.oauth;

import cn.hutool.json.JSONObject;
import com.wkrj.core.configuration.security.SecurityUserDetailsService;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Oauth服务器，提供数据接口
 *
 * @author ziro
 * @date 2020/5/14 14:44
 */
@Controller
@RequestMapping("oauth/server")
public class OauthServerInterfaceController {
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping("userInfo")
    @ResponseBody
    public Object getUserSimple() {
        WkrjUserNew user = securityUserDetailsService.loadCurrentUserInfo();
        JSONObject json = new JSONObject();
        json.put("id", user.getUserId());
        json.put("name", user.getUserRealname());
        json.put("username", user.getUserName());
        System.out.println(json);
        System.out.println(json.toJSONString(0));
        System.out.println(json.toStringPretty());
        System.out.println(json.toString());
        return json.toString();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    @RequestMapping("user")
    @ResponseBody
    public Object getUser() {
        WkrjUserNew user = securityUserDetailsService.loadCurrentUserInfo();
        return user;
    }
}
