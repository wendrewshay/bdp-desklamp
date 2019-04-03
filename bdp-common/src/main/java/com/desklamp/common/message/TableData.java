package com.desklamp.common.message;

import java.io.Serializable;
import java.util.List;

/**
 * table分页属性
 * @author jw
 * @date 2019/3/25
 */
public class TableData<T> implements Serializable {

    private static final long serialVersionUID = -6177599350327259212L;

    /**
     * 默认每页的条数
     */
    private static final int DEFAULT_LENGTH = 10;
    /**
     * 默认的页数
     */
    private static final int DEFAULT_PAGE_NO = 0;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 页数
     */
    private int pageNum;

    /**
     * 每页的条数
     */
    private int length;

    /**
     * 列表总数
     */
    private Integer total;

    public TableData(List<T> list, int pageNum, int length, Integer total) {
        this.list = list;
        this.pageNum = pageNum;
        this.length = length;
        this.total = total;
    }

    public int pageNumber(){
        if(pageNum < 0){
            pageNum = DEFAULT_PAGE_NO;
        }
        if(length <= 0 ){
            length = DEFAULT_LENGTH;
        }
        return pageNum / length + 1;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
