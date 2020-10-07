package com.wkrj.core.configuration.security;

import com.wkrj.core.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 密码加密方法
 *
 * @author ziro
 * @date 2020/5/1 22:43
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {
    private static String SALT = "mypasswordsalt";

    /**
     * 密码加密方法
     *
     * @param password
     * @return
     */
    @Override
    public String encode(CharSequence password) {
        return MD5.MD5Encode(password.toString());
    }

    /**
     * 重写判断方法，即判断明文密码是否匹配，也判断加密后的密码是否匹配
     *
     * @param password        待比较密码
     * @param encodedPassword 加密后密码
     * @return
     */
    @Override
    public boolean matches(CharSequence password, String encodedPassword) {
        try {
            String enc = encode(password.toString());
            return enc.equals(encodedPassword) || password.toString().equals(encodedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
