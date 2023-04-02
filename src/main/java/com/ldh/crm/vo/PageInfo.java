package com.ldh.crm.vo;

import lombok.Data;

@Data
public class PageInfo {
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
