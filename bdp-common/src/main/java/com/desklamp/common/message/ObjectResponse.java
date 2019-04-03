package com.desklamp.common.message;


/**
 * 返回的对象数据
 * @author jw
 * @date 2019/3/25
 */
public class ObjectResponse<T> extends BaseResponse {

    private T data;

    public ObjectResponse() {
        super();
    }

    public ObjectResponse(String code, String msg,boolean success) {
        super(code, msg,success);
    }

    public ObjectResponse(String code, String msg, T data,boolean success) {
        super(code, msg,success);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
