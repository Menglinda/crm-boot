package com.ldh.crm.service;

import com.ldh.crm.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ldh.crm.pojo.Review;

import java.util.List;

/**
* @author 35108
* @description 针对表【article】的数据库操作Service
* @createDate 2023-04-06 22:33:30
*/
public interface ArticleService extends IService<Article> {

    Integer addArticle(Article article);
}
