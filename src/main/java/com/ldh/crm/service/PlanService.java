package com.ldh.crm.service;

import com.ldh.crm.pojo.Plan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 35108
* @description 针对表【plan】的数据库操作Service
* @createDate 2023-04-17 16:32:57
*/
public interface PlanService extends IService<Plan> {

    List<Plan> findByDate(String date);
}
