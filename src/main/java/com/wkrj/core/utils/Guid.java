package com.wkrj.core.utils;

import java.util.UUID;

public class Guid {

    /**
     * 生成36位UUID
     * @return
     */
    public static String getGuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成32位UUID，不含-
     * @return
     */
    public static String getGuid32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        //System.out.println(getGuid());
        //System.out.println(getGuid32());
    }
}
