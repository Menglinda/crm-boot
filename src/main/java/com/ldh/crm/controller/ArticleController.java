package com.ldh.crm.controller;


import cn.hutool.core.date.DateUtil;
import com.ldh.crm.pojo.Article;
import com.ldh.crm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @PostMapping("/addArticleByUser")
    public Integer addArticleByUser(@RequestBody Article article) {
        Integer count=0;
        if (article.getName() != null && article.getType() != null && article.getContent() != null) {
            article.setTime(DateUtil.now());
            count = articleService.addArticle(article);
        }
        return count;
    }

    @GetMapping("/getArticle/{id}")
    public Article getArticleById(@PathVariable Integer id){
        Article article = articleService.getById(id);
        return article;
    }
}
