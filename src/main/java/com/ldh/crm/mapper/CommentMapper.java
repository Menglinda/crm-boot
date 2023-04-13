package com.ldh.crm.mapper;

import com.ldh.crm.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 35108
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-04-12 21:04:55
* @Entity com.ldh.crm.pojo.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select c.*, u.nickname, u.avatar_url from comment c left join userinfo u on c.user_id = u.id where c.article_id = #{articleId} order by id desc ")
    List<Comment> findComment(Integer articleId);
}




