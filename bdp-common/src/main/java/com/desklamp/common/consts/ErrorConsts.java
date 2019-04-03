package com.desklamp.common.consts;

/**
 * 自定义状态码和消息
 * @author jw
 * @date 2019/3/22
 */
public enum ErrorConsts {

    /*******************返回结果************************/
    /**
     * 请求成功
     */
    SUCCESS("200","请求成功"),

    /**
     * 权限不够不能正常访问
     */
    NO_AUTHORITY("403","权限不够"),

    ERROR_SERVER("500","操作异常"),


    /*******************通用异常************************/
    INVALID_PARAM_EXCEPTION("BDP001","无效参数异常"),
    INVALID_ACCOUNT_EXCEPTION("BDP002","无效账户异常"),
    INVALID_M2J_EXCEPTION("BDP003","Map转javaBean异常"),

    /*******************OCR异常************************/
    INVALID_OCR_TEST("R001","OCR异常"),
    ERROR_OCR_RECOGNITION("R002","OCR识别异常"),
    ERROR_NO_DATA("R003","OCR查询无数据"),
    ERROR_NO_ADDRESS("R004","获取图片地址失败"),
    ERROR_NO_BUSINESS_LICENSE("R005","OCR识别营业执照信息异常"),
    ERROR_NO_IDCARD_A("R006","OCR识别身份证正面信息异常"),
    ERROR_NO_IDCARD_B("R007","OCR识别身份证反面信息异常"),
    ERROR_NO_IDCARD("R008","OCR识别身份证信息异常"),

    /*******************电子合同发票异常********************/
    INVOICE_NOT_NULL_PICTURE("E001","传入图片不能为空!"),
    ERROR_NO_RECOGNITION_PICTURE("E002","识别发票图片数据为空"),
    INVOICE_ERROR_CHECK("E003","发票校验异常"),
    ERROR_PICTURE_FORMAT("E004","只支持jpg格式数据"),
    ERROR_RECOGNITION_PICTURE("E005","识别发票图片数据异常或图片不清晰"),


    /*******************短信服务异常********************/
    INVALID_MESSAGE_TEST("M001","短信服务异常")



    ;

    ErrorConsts(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

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
}
