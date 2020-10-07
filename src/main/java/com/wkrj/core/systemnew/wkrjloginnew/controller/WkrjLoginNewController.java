package com.wkrj.core.systemnew.wkrjloginnew.controller;

import com.wkrj.core.component.jwt.JwtToken;
import com.wkrj.core.configuration.security.MyPasswordEncoder;
import com.wkrj.core.configuration.security.SecurityUserDetailsService;
import com.wkrj.core.systemnew.menu.service.WkrjMenuNewService;
import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.role.service.WkrjRoleNewService;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.systemnew.wkrjloginnew.service.WkrjNewLonginService;
import com.wkrj.core.utils.LayuiJson;
import com.wkrj.core.utils.ReadPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author ziro
 * @date 2020年5月8日 18:52
 */
@Controller
@RequestMapping("wkrjsystemnew/wkrjlogin")
public class WkrjLoginNewController {

    @Autowired
    private WkrjMenuNewService menuService;
    @Autowired
    private WkrjRoleNewService roleService;
    @Autowired
    private WkrjNewLonginService wkrjNewLonginService;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;


    /**
     * 登录方法
     *
     * @param username
     * @param password
     * @param yzm
     * @param req
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public LayuiJson login(String username, String password, String yzm, HttpServletRequest req) {
        LayuiJson json = new LayuiJson();
		/*String realYzm = (String) req.getSession().getAttribute("validateCode");		
		if(null==yzm || !yzm.equalsIgnoreCase(realYzm)){
			json.setMsg("验证码输入错误");
			json.setSuccess(false);
			return json;
		}*/
        WkrjUserNew usernew = new WkrjUserNew();
        usernew.setUserName(username);
        usernew.setUserPassword(myPasswordEncoder.encode(password));
        String userId = wkrjNewLonginService.findUserByNameAndPwd(usernew);
        if (null != userId) {
            try {
                //调用security验证处理
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
                Authentication authentication = authenticationManager.authenticate(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                //密码验证成功
                usernew = securityUserDetailsService.loadCurrentUserInfo();
                String user_is_enable = usernew.getUserIsEnable();
                if ("1".equals(user_is_enable)) {
                    json.setSuccess(false);
                    json.setMsg("用户名被禁用！");
                    return json;
                }

                //注意
                //req.getSession().setAttribute("user", user);//将user对象放入sessin中
                //将user对象放入sessin中
                req.getSession().setAttribute("usernew", usernew);
                //将user类型放入sessin中
                req.getSession().setAttribute("userCountType", usernew.getUserAccounttype());
                if (1 == usernew.getUserAccounttype()) {
                    req.getSession().setAttribute("userPermission", roleService.getAllPermission());
                } else {
                    String roleId = "";
                    if (usernew != null) {
                        List<WkrjRoleNew> user_role = usernew.getUserRole();
                        if (user_role.size() > 0) {
                            for (WkrjRoleNew uRole : user_role) {
                                roleId += uRole.getRoleId();
                                roleId += ",";
                            }
                        }
                    }
                    req.getSession().setAttribute("userPermission", roleService.getRolePermissionByRoleId(roleId.substring(0, roleId.length() - 1)));
                }

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user_realname", usernew.getUserRealname());

                //设置access_token
                String access_token = "";
                try {
                    access_token = JwtToken.createToken(userId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("access_token", access_token);

                //如果是认证过来页面，需要跳转回待认证的页，其他则正常进入首页
                Object retUrl = req.getSession().getAttribute("tooauth_url");
                if (retUrl != null && !"".equals(retUrl.toString())) {
                    map.put("isoauth", true);
                    map.put("oauthurl", retUrl);
                    req.getSession().removeAttribute("tooauth_url");
                }

                json.setData(map);
                json.setMsg("登录成功");
                json.setSuccess(true);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            json.setSuccess(false);
            json.setMsg("用户名或者密码错误！");
        }
        return json;
    }

    /**
     * 退出
     *
     * @param request
     * @return
     */
    @RequestMapping("logout")
    @ResponseBody
    public LayuiJson logout(HttpServletRequest request) {
        LayuiJson j = new LayuiJson();
        j.setCode(1001);//退出编码
        try {
            //清理Session
            Enumeration<String> attributeNames = request.getSession().getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                request.getSession().removeAttribute(attributeNames.nextElement());
            }
            request.getSession().invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 检测登录信息
     *
     * @return
     */
    @RequestMapping("checkLogin")
    @ResponseBody
    public LayuiJson checkLogin() {
        LayuiJson j = new LayuiJson();
        try {
            WkrjUserNew userNew = securityUserDetailsService.loadCurrentUserInfo();
            if (userNew == null) {
                j.setCode(1001);
                j.setSuccess(false);
                j.setMsg("登录信息已过期，请重新登录！");
                return j;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        j.setSuccess(true);
        return j;
    }

    /**
     * 跳转认证的中间地址，记录认证地址（用于登录）
     *
     * @param request
     * @return
     */
    @RequestMapping("tooauth")
    public String tooauth(HttpServletRequest request) {
        String oauthServer = ReadPropertiesUtil.readProperty("oauth.serverUrl");
        try {
            String response_type = request.getParameter("response_type");
            String state = request.getParameter("state");
            String client_id = request.getParameter("client_id");
            String redirect_uri = request.getParameter("redirect_uri");
            String scope = request.getParameter("scope");
            String tourl = oauthServer + "oauth/authorize?" +
                    (StringUtils.isEmpty(response_type) ? "response_type=code" : "response_type=" + response_type) +
                    (StringUtils.isEmpty(state) ? "" : "&state=" + state) +
                    (StringUtils.isEmpty(client_id) ? "" : "&client_id=" + client_id) +
                    (StringUtils.isEmpty(redirect_uri) ? "" : "&response_type=" + redirect_uri) +
                    (StringUtils.isEmpty(scope) ? "" : "&scope=" + scope);
            if (request.getSession().getAttribute("usernew") == null) {
                //未登录，记录授权地址
                request.getSession().setAttribute("tooauth_url", tourl);
            }
            return "redirect:" + tourl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + ReadPropertiesUtil.readProperty("security.loginurl");
    }
}
