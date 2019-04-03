package com.desklamp.common.message;

import java.util.List;

/**
 * 返回对象的列表数据
 * @author jw
 * @date 2019/3/25
 */
public class ListResponse<T> extends BaseResponse {

    private List<T> data;

    public ListResponse(String code, String msg,boolean success) {
        super(code, msg,success);
    }

    public ListResponse(String code, String msg, List<T> data,boolean success) {
        super(code, msg,success);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
