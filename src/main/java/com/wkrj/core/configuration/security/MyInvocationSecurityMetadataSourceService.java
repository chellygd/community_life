package com.wkrj.core.configuration.security;

import com.wkrj.core.utils.ReadPropertiesUtil;
import com.wkrj.core.systemnew.role.dao.WkrjRoleNewDao;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.regex.Pattern.compile;

/**
 * 从数据库加载权限数据
 *
 * @author ziro
 * @date 2020/5/5 14:58
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    private HashMap<String, Collection<ConfigAttribute>> map = null;
    @Autowired
    private WkrjRoleNewDao wkrjRoleNewDao;
    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    /**
     * 加载资源，初始化资源变量（仅第一次加载时查询）
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        //查询全部【权限地址，角色id】的对应关系
        List<Map<String, Object>> rolePermissionList = wkrjRoleNewDao.getAllRolePermission();
        for (Map<String, Object> rpMap : rolePermissionList) {
            //map的  key=权限地址，value=role_id的数组
            //该步骤先查出已关联key的role_id数组
            array = map.get(rpMap.get("perm_flag") + "");
            if (array == null) {
                array = new ArrayList<>();
            }
            //把遍历的role_id放入数组中，然后把数组放入map中
            cfg = new SecurityConfig(SecurityUserDetails.defaultRolePrefix + rpMap.get("role_id"));
            array.add(cfg);
            map.put(rpMap.get("perm_flag") + "", array);
        }
    }

    /**
     * 遍历所有权限列表，取所需角色
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String thisurl = request.getRequestURI();
        //1判断放过拦截名单
        String permitUrl = ReadPropertiesUtil.readFile(ReadPropertiesUtil.readProperty("wkrj.security.ignore"));
        if (permitUrl.indexOf(thisurl + ",") >= 0) {
            //在放过名单中
            return null;
        } else {
            //判断**
            String[] permitArr = permitUrl.split(",");
            for (String permit : permitArr) {
                if (permit.indexOf("**") >= 0) {
                    //正则表达式相匹配 ^表示以什么开头   .*  表示0-n个字符
                    if (compile("^" + permit.replace("**", ".*")).matcher(thisurl).find()) {
                        return null;
                    }
                }
            }
        }
        //2判断超级管理员
        WkrjUserNew user = securityUserDetailsService.loadCurrentUserInfo();
        if (user != null && user.getUserAccounttype() == 1) {
            //开发管理员，不验证
            return null;
        }
        //3查询所需授权
        if (map == null) {
            loadResourceDefine();
        }
        AntPathRequestMatcher matcher;
        String resUrl;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
