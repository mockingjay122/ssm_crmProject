package com.gj.crm.commons.domian;

/**
 * @author 郭嘉
 * @date 2022/9/16 - 20:08
 */
public class ReturnObject {
    //处理成功或者失败的消息
    private String code;
    //提示信息
    private String message;
    //其他数据
    private Object returnData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }
}
