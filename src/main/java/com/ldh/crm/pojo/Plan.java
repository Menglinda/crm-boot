package com.ldh.crm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName plan
 */
@TableName(value = "plan")
@Data
public class Plan implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String date;

    /**
     *
     */
    private String content;

    private String nickname;

    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}