package com.desklamp.common.message;

/**
 * 分页数据的返回结果
 * @author jw
 * @date 2019/3/25
 */
public class TableResponse<T> extends BaseResponse {

    private TableData<T> tableData;

    public TableResponse(String code, String msg,boolean success) {
        super(code, msg,success);
    }

    public TableResponse(String code, String msg, TableData<T> tableData, boolean success) {
        super(code, msg,success);
        this.tableData = tableData;
    }

    public TableData<T> getTableData() {
        return tableData;
    }

    public void setTableData(TableData<T> tableData) {
        this.tableData = tableData;
    }
}
