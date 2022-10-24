package com.gj.crm.commons.utils;


import java.util.UUID;

/**
 * @author 郭嘉
 * @date 2022/10/2 - 15:25
 */
public class UUIDUtils {
    //获取uuid
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
