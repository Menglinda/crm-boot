package com.ldh.crm.service;

import com.ldh.crm.pojo.Userinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 35108
* @description 针对表【userinfo】的数据库操作Service
* @createDate 2023-04-12 18:53:43
*/
public interface UserinfoService extends IService<Userinfo> {

    Userinfo queryByNickname(String nickname);
}
