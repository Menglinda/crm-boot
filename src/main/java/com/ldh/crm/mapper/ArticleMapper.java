package com.ldh.crm.mapper;

import com.ldh.crm.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldh.crm.pojo.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 35108
* @description 针对表【article】的数据库操作Mapper
* @createDate 2023-04-06 22:33:30
* @Entity com.ldh.crm.pojo.Article
*/

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select * from article where nickname = #{nickname}")
    List<Article> getByNickname(@Param("nickname") String nickname);
}




