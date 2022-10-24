package com.gj.crm.commons.utils;

/**
 * @author 郭嘉
 * @date 2022/10/9 - 16:38
 */
public class StringUtils {


    //将ids拆分
    public static String[] buildIds(String ids){
        //调用service方法
        String []secIds=ids.split("&");
        String []finalIds=new String[secIds.length];
        int index=0;
        for (String secId : secIds) {
            String str1=secId.substring(0,secId.indexOf("="));
            String str2=secId.substring(str1.length()+1,secId.length());
            finalIds[index++]=str2;
        }

        return finalIds;
    }
}
