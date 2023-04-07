package com.ldh.crm.service;

import com.ldh.crm.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ldh.crm.pojo.Article;
import com.ldh.crm.pojo.User;

/**
 * @author 35108
 * @description 针对表【admin】的数据库操作Service
 * @createDate 2023-03-31 14:53:26
 */
public interface AdminService extends IService<Admin> {
    String adminLogin(Admin admin);

    String addUser(User user);

    Boolean updatePsdById(String email,String pass);

    Integer addArticle(Article article);
}
