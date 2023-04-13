package com.ldh.crm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName userinfo
 */
@TableName(value ="userinfo")
@Data
public class Userinfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String email;
    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private String profile;

    /**
     * 
     */
    private String avatarUrl;

    /**
     * 
     */
    private Object points;

    /**
     * 
     */
    private Integer sex;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}