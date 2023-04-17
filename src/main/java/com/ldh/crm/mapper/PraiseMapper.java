package com.ldh.crm.mapper;

import com.ldh.crm.pojo.Praise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 35108
* @description 针对表【praise】的数据库操作Mapper
* @createDate 2023-04-14 21:16:05
* @Entity com.ldh.crm.pojo.Praise
*/
@Mapper
public interface PraiseMapper extends BaseMapper<Praise> {

    @Select("select * from praise where nickname = #{nickname}")
    List<Praise> getByNickname(@Param("nickname") String nickname);


    @Select("select * from praise where author = #{author}")
    List<Praise> getByAuthor(@Param("author") String author);
}




