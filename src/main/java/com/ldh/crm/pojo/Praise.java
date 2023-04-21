package com.ldh.crm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName praise
 */
@TableName(value ="praise")
@Data
public class Praise implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer articleId;
    /**
     * 
     */
    private String nickname;
    private String author;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}