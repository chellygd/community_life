package com.wkrj.core.configuration.security;

import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.systemnew.user.dao.WkrjUserNewDao;
import com.wkrj.core.systemnew.wkrjloginnew.dao.WkrjNewLoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

/**
 * @author ziro
 * @date 2020/5/5 17:45
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private WkrjUserNewDao wkrjUserNewDao;
    @Autowired
    private WkrjNewLoginDao wkrjNewLoginDao;

    /**
     * 重写security方法，查询用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WkrjUserNew user = null;
        try {
            user = wkrjUserNewDao.findUserInfoByName(username);
            if (user != null) {
                user = wkrjNewLoginDao.getUserInfoById(user.getUserId());
                return new SecurityUserDetails(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载（当前登录）用户信息
     */
    public WkrjUserNew loadCurrentUserInfo() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        try {
            if (authentication instanceof OAuth2Authentication &&
                    (principal instanceof String || principal instanceof org.springframework.security.core.userdetails.User)) {
                SecurityUserDetails userDetails = (SecurityUserDetails) principal;
                return userDetails.getCurrentUser();
            } else {
                final SecurityUserDetails userDetails = (SecurityUserDetails) principal;
                return userDetails.getCurrentUser();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
}
