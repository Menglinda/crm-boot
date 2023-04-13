package com.ldh.crm.service;

import com.ldh.crm.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 35108
* @description 针对表【comment】的数据库操作Service
* @createDate 2023-04-12 21:04:55
*/
public interface CommentService extends IService<Comment> {

    List<Comment> findComment(Integer articleId);
}
