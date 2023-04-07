package com.ldh.crm.mapper;

import com.ldh.crm.pojo.Review;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 35108
 * @description 针对表【review】的数据库操作Mapper
 * @createDate 2023-04-07 20:55:34
 * @Entity com.ldh.crm.pojo.Review
 */

@Mapper
public interface ReviewMapper extends BaseMapper<Review> {
    @Select("select c.*,u.nickname from review c left join user u on c.nickname=u.nickname where c.articleId=#{articleId} ;")
    List<Review> findComment(@Param("articleId") Integer articleId);
}




