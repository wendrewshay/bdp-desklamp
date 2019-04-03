package com.desklamp.common.message;

/**
 * 响应模型
 * @author: Erin
 * @create: 2019-03-24 11:20
 **/
public class BaseResponse {

    private String code;
    private String msg;
    private boolean success;

    public BaseResponse() {
        super();
    }

    public BaseResponse(String code, String msg,boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
