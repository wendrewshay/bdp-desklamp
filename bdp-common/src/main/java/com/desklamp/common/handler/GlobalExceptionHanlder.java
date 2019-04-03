package com.desklamp.common.handler;

/**
 * 全局异常处理
 * @author jw
 * @date 2019/3/22
 */
public class GlobalExceptionHanlder extends RuntimeException {

    private String code;
    private String message;

    public GlobalExceptionHanlder(){
        super();
    }

    public GlobalExceptionHanlder(String code,String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public GlobalExceptionHanlder(String code,String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    public GlobalExceptionHanlder(Throwable cause) {
        super(cause);
    }

    public GlobalExceptionHanlder(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        return "GlobalExceptionHanlder{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
