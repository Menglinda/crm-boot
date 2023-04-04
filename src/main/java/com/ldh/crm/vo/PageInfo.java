package com.ldh.crm.vo;

import lombok.Data;

@Data
public class PageInfo {
    private String query;
    private Integer pageNum;
    private Integer pageSize;
}
