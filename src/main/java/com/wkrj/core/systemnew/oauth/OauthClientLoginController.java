package com.wkrj.core.systemnew.oauth;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wkrj.core.utils.ReadPropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * Oauth客户端
 *
 * @author ziro
 * @date 2020/5/13 15:11
 */
@Controller
public class OauthClientLoginController {

    private String oauthServerUrl = ReadPropertiesUtil.readProperty("oauth.serverUrl");
    private String clientId = ReadPropertiesUtil.readProperty("oauth.clientId");
    private String clientSecret = ReadPropertiesUtil.readProperty("oauth.clientSecret");

    /**
     * 客户端回调方法=====此方法在客户端使用
     *
     * @param request
     * @return
     */
    @RequestMapping("login/code")
    public String login(HttpServletRequest request) {
        try {
            String error = request.getParameter("error");
            if (error != null) {
                //授权失败，用户未同意授权
                return "redirect:" + ReadPropertiesUtil.readProperty("wkrj.security.loginurl");
            } else {
                //1，使用code换取token，code将失效
                String code = request.getParameter("code");
                if (code != null && code.length() > 0) {
                    //【使用code换取token】
                    HashMap<String, Object> paramMap = new HashMap<>(5);
                    paramMap.put("client_id", clientId);
                    paramMap.put("client_secret", clientSecret);
                    paramMap.put("grant_type", "authorization_code");
                    paramMap.put("code", code);
                    paramMap.put("redirect_uri", oauthServerUrl + "login/code");
                    String result = HttpUtil.post(oauthServerUrl + "oauth/token?", paramMap);
                    if (result != null && result.length() > 0 && result.indexOf("access_token") >= 0) {
                        JSONObject obj = JSONUtil.parseObj(result);
                        if (obj.get("access_token") != null && obj.getStr("access_token").length() > 0) {
                            //2，使用access_token换取用户信息
                            String token = obj.getStr("access_token");
                            //【使用token换取用户信息】
                            paramMap = new HashMap<>(1);
                            paramMap.put("access_token", token);
                            String result2 = HttpUtil.post(oauthServerUrl + "oauth/server/userInfo?", paramMap);

                            //授权成功，获得用户信息，如：openid等
                            //自己写登录逻辑，跳转至登录成功界面
                            String sss = URLDecoder.decode(result2, "UTF-8");
                            System.out.println(sss);
                            return "用户信息：" + result2;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + ReadPropertiesUtil.readProperty("wkrj.security.loginurl");
    }

}
