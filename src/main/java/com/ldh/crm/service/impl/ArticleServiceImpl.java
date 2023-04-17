package com.ldh.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Article;
import com.ldh.crm.service.ArticleService;
import com.ldh.crm.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 35108
* @description 针对表【article】的数据库操作Service实现
* @createDate 2023-04-06 22:33:30
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public Integer addArticle(Article article) {
        int insert = articleMapper.insert(article);
        return insert;
    }

    @Override
    public List<Article> getByNickname(String nickname) {
        return articleMapper.getByNickname(nickname);
    }

    @Override
    public List<Article> getMostHot() {
        return articleMapper.getMostHot();
    }

    @Override
    public List<Article> getMostNew() {
        return articleMapper.getMostNew();
    }
}




