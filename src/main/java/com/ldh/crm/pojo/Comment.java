package com.ldh.crm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @TableName comment
 */
@TableName(value = "comment")
@Data
public class Comment implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String content;

    /**
     *
     */
    private Integer userId;

    /**
     *
     */
    private String time;

    /**
     *
     */
    private Integer pid;
    @TableField(exist = false)
    private String pNickname;
    @TableField(exist = false)
    private Integer pUserId;

    /**
     *
     */
    private Integer originId;

    /**
     *
     */
    private Integer articleId;


    @TableField(exist = false)
    private String nickname;

    @TableField(exist = false)
    private String avatarUrl;

    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}