package com.ldh.crm.service;

import com.ldh.crm.pojo.Article;
import com.ldh.crm.pojo.Collect;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 35108
* @description 针对表【collect】的数据库操作Service
* @createDate 2023-04-14 20:17:35
*/
public interface CollectService extends IService<Collect> {

    List<Collect> getByNickname(String nickname);

    List<Collect> getByAuthor(String author);
}
