package com.ldh.crm.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Admin;
import com.ldh.crm.pojo.User;
import com.ldh.crm.service.UserService;
import com.ldh.crm.mapper.UserMapper;
import com.ldh.crm.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 35108
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-04-01 15:05:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private  UserMapper userMapper;


}




