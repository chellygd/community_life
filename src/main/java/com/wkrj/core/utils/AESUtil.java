package com.wkrj.core.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Aes方式对称加密解密
 *
 * @author ziro
 * @date 2020/8/11 14:21
 */
@Component
public class AESUtil {
    //key+salt（规则16/32位，英文和数字）
    private static final String AES_SALT = "wkrj2020wkrj2020wkrj2020wkrj2020";

    /**
     * 加密
     *
     * @param value
     * @return
     */
    public static String encrypt(String value) {
        return encrypt(value, AES_SALT);
    }

    /**
     * 加密（自定义加盐）
     *
     * @param value
     * @param saltKey 规则16/32位，英文和数字
     * @return
     */
    public static String encrypt(String value, String saltKey) {
        //密钥
        byte[] key = saltKey.getBytes();
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        return aes.encryptHex(value);
    }

    /**
     * 解密
     *
     * @param value
     * @return
     */
    public static String decrypt(String value) {
        return decrypt(value, AES_SALT);
    }

    /**
     * 解密（自定义加盐）
     *
     * @param value
     * @param saltKey 规则16/32位，英文和数字
     * @return
     */
    public static String decrypt(String value, String saltKey) {
        //密钥
        byte[] key = saltKey.getBytes();
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        return aes.decryptStr(value, CharsetUtil.CHARSET_UTF_8);
    }


    public static void main(String[] args) {
        String content = "99999999999999999999";
        System.out.println(AESUtil.encrypt(content));
        System.out.println(AESUtil.decrypt(AESUtil.encrypt(content)));
        System.out.println(AESUtil.encrypt(content, "abcd1234abcd1234kkkk2020tttt2020"));
        System.out.println(AESUtil.decrypt(AESUtil.encrypt(content, "abcd1234abcd1234kkkk2020tttt2020"), "abcd1234abcd1234kkkk2020tttt2020"));
    }

}
