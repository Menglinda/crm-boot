package com.ldh.crm.mapper;

import com.ldh.crm.pojo.Userinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author 35108
* @description 针对表【userinfo】的数据库操作Mapper
* @createDate 2023-04-12 18:53:43
* @Entity com.ldh.crm.pojo.Userinfo
*/
@Mapper
public interface UserinfoMapper extends BaseMapper<Userinfo> {

    @Select("select * from userinfo where nickname = #{nickname}")
    Userinfo selectByNickname(@Param("nickname") String nickname);
}




