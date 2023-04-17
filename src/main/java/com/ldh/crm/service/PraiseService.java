package com.ldh.crm.service;

import com.ldh.crm.pojo.Collect;
import com.ldh.crm.pojo.Praise;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 35108
* @description 针对表【praise】的数据库操作Service
* @createDate 2023-04-14 21:16:05
*/
public interface PraiseService extends IService<Praise> {

    List<Praise> getByNickname(String nickname);

    List<Praise> getByAuthor(String author);
}
