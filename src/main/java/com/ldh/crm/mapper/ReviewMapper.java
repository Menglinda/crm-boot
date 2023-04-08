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
* @createDate 2023-04-08 10:49:17
* @Entity com.ldh.crm.pojo.Review
*/

@Mapper
public interface ReviewMapper extends BaseMapper<Review> {

    @Select("select * from review where article_id = #{articleId} order by id desc")
    List<Review> getReview(@Param("articleId") Integer articleId);
}




