package com.ldh.crm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName review
 */
@TableName(value ="review")
@Data
public class Review implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private String time;

    /**
     * 
     */
    private String pnickname;

    /**
     * 
     */
    private String originnickname;

    /**
     * 
     */
    private Integer articleid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}