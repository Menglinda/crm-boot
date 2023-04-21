package com.ldh.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Plan;
import com.ldh.crm.service.PlanService;
import com.ldh.crm.mapper.PlanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 35108
* @description 针对表【plan】的数据库操作Service实现
* @createDate 2023-04-17 16:32:57
*/
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan>
    implements PlanService{

    @Resource
    private PlanMapper planMapper;

    @Override
    public List<Plan> findByDate(String date) {
        return planMapper.findByDate(date);
    }
}




