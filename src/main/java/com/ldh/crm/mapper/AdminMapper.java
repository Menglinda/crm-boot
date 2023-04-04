package com.ldh.crm.mapper;

import com.ldh.crm.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
* @author 35108
* @description 针对表【admin】的数据库操作Mapper
* @createDate 2023-03-31 14:53:26
* @Entity com.ldh.crm.pojo.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    @Update("update admin set password = #{pass} where email = #{email}")
    public boolean updatePassword(@Param("email") String email,@Param("pass") String pass);
}




