package com.ldh.crm.mapper;

import com.ldh.crm.pojo.Plan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 35108
* @description 针对表【plan】的数据库操作Mapper
* @createDate 2023-04-17 16:32:57
* @Entity com.ldh.crm.pojo.Plan
*/
@Mapper
public interface PlanMapper extends BaseMapper<Plan> {

    @Select("select * from plan where date = #{date};")
    List<Plan> findByDate(@Param("date") String date);
}




