package com.ldh.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Comment;
import com.ldh.crm.service.CommentService;
import com.ldh.crm.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 35108
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-04-12 21:04:55
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> findComment(Integer articleId) {
        List<Comment> comment = commentMapper.findComment(articleId);
        return comment;

    }
}




