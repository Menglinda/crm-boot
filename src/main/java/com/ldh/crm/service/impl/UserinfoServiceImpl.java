package com.ldh.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.service.UserinfoService;
import com.ldh.crm.mapper.UserinfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 35108
* @description 针对表【userinfo】的数据库操作Service实现
* @createDate 2023-04-12 18:53:43
*/
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo>
    implements UserinfoService{

    @Resource
    private UserinfoMapper userinfoMapper;

    @Override
    public Userinfo queryByNickname(String nickname) {
        Userinfo userinfo = userinfoMapper.selectByNickname(nickname);
        return userinfo;

    }
}




