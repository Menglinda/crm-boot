package com.ldh.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldh.crm.pojo.Plan;
import com.ldh.crm.service.PlanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/plan")
public class PlanController {

    @Resource
    private PlanService planService;

    @GetMapping("/findAll/{nickname}")
    public List<Plan> findAll(@PathVariable String nickname) {
        QueryWrapper<Plan> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", nickname);
        List<Plan> list = planService.list(wrapper);
        return list;
    }


    @PostMapping("/savePlan")
    public boolean savePlan(@RequestBody Plan plan) {
        String date = plan.getDate();
        List<Plan> list = planService.findByDate(date);
        boolean flag = false;
        if (list.size() == 0) {
            flag = planService.save(plan);
        } else {
            QueryWrapper<Plan> wrapper = new QueryWrapper<>();
            wrapper.eq("date", date);
            planService.remove(wrapper);
            flag = planService.save(plan);
        }
        return flag;
    }

    @GetMapping("/setStatus/{nickname}/{date}")
    public boolean setStatus(@PathVariable String nickname, @PathVariable String date) {
        QueryWrapper<Plan> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", nickname);
        wrapper.eq("date", date);
        Plan one = planService.getOne(wrapper);
        one.setStatus(1);
        return planService.updateById(one);
    }

    @GetMapping("/getStatus/{nickname}/{date}")
    public Integer getStatus(@PathVariable String nickname, @PathVariable String date) {
        QueryWrapper<Plan> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", nickname);
        wrapper.eq("date", date);
        Plan one = planService.getOne(wrapper);
        if (one !=null){
            return one.getStatus();
        }else {
            return 0;
        }

    }
}
