package com.ldh.crm.mapper;

import com.ldh.crm.pojo.Article;
import com.ldh.crm.pojo.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 35108
* @description 针对表【collect】的数据库操作Mapper
* @createDate 2023-04-14 20:17:35
* @Entity com.ldh.crm.pojo.Collect
*/
@Mapper
public interface CollectMapper extends BaseMapper<Collect> {


    @Select("select * from collect where nickname = #{nickname}")
    List<Collect> getByNickname(@Param("nickname") String nickname);


    @Select("select * from collect where author = #{author}")
    List<Collect> getByAuthor(String author);
}




