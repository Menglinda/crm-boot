package com.ldh.crm.service;


import com.ldh.crm.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;


public interface UserService extends IService<User> {

    List<User> selectByNickname( String nickname);

    Boolean  sendEmail(String email,Integer type);
}
