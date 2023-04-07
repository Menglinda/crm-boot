package com.ldh.crm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {

    @TableId
    private Integer id;

    private String name;


    private String type;


    private String content;

    private String nickname;


    private String time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}