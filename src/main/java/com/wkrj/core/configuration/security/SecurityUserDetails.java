package com.wkrj.core.configuration.security;

import com.wkrj.core.systemnew.role.bean.WkrjRoleNew;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Spring Security中的UserDetails实现
 *
 * @author ziro
 * @date 2020/4/29 22:40
 */
public class SecurityUserDetails implements UserDetails {
    private static final long serialVersionUID = 1191569078974442568L;

    //定义用户信息
    private WkrjUserNew user;
    //用户的授权集合
    private List<GrantedAuthority> grantedAuthorities = new ArrayList<>(16);
    //默认角色前缀
    public static String defaultRolePrefix = "ROLE_";

    //初始化用户信息
    SecurityUserDetails(WkrjUserNew user) {
        this.user = user;
        // 初始化用户信息，角色权限
        List<WkrjRoleNew> roleList = user.getUserRole();
        for (WkrjRoleNew WkrjRole : roleList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(defaultRolePrefix + WkrjRole.getRoleName()));
        }
    }

    /**
     * 获取用户授权集合
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public WkrjUserNew getCurrentUser() {
        return user;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 获取密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    /**
     * 账户是否未过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否启用,默认true (启用)
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
