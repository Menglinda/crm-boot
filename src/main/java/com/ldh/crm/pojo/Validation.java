package com.ldh.crm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName validation
 */
@TableName(value = "validation")
@Data
public class Validation implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String email;

    /**
     *
     */
    private String code;

    /**
     *
     */
    private Date time;

    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}