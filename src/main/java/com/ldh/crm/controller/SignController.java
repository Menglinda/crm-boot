package com.ldh.crm.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldh.crm.pojo.Sign;
import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.service.SignService;
import com.ldh.crm.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/sign")
public class SignController {

    @Autowired
    private SignService signService;

    @Autowired
    private UserinfoService userinfoService;

    @GetMapping("/isSign/{nickname}")
    public Integer isSign(@PathVariable String nickname) {
        String today = DateUtil.today();
        QueryWrapper<Sign> wrapper = new QueryWrapper<>();
        wrapper.eq("time", today);
        wrapper.eq("nickname", nickname);
        Sign one = signService.getOne(wrapper);
        if (one != null) {
            return 0;
        } else {
            return 1;
        }

    }

    @GetMapping("/signIn/{nickname}")
    public Integer signIn(@PathVariable String nickname) {
        String today = DateUtil.today();
        QueryWrapper<Sign> wrapper = new QueryWrapper<>();
        wrapper.eq("time", today);
        wrapper.eq("nickname", nickname);
        Sign one = signService.getOne(wrapper);
        if (one != null) {
            return 0;
        } else {
            Sign sign = new Sign();
            sign.setNickname(nickname);
            sign.setTime(today);
            signService.save(sign);
            Userinfo userinfo = userinfoService.queryByNickname(nickname);
            if (userinfo != null) {
                Integer points = userinfo.getPoints();
                points += 10;
                userinfo.setPoints(points);
                userinfoService.updateById(userinfo);
            }
            return 1;
        }

    }
}
