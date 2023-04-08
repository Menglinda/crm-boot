package com.ldh.crm.mapper;

import com.ldh.crm.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldh.crm.vo.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 35108
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-04-01 15:05:16
* @Entity com.ldh.crm.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user limit #{pageNum},#{pageSize}")
    List<User> queryUsers(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize);

    @Select("select * from user where nickname = #{nickname}")
    List<User> selectByNickname(@Param("nickname") String nickname);
}




