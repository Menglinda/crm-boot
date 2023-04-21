package com.ldh.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldh.crm.pojo.Article;
import com.ldh.crm.pojo.Collect;
import com.ldh.crm.pojo.Praise;
import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.service.ArticleService;
import com.ldh.crm.service.CollectService;
import com.ldh.crm.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/collectArticle/{id}/{nickname}/{author}")
    public Boolean collectArticle(@PathVariable Integer id, @PathVariable String nickname, @PathVariable String author) {
        Collect collect = new Collect();
        collect.setArticleId(id);
        collect.setNickname(nickname);
        collect.setAuthor(author);
        boolean save = collectService.save(collect);
        Userinfo userinfo = userinfoService.queryByNickname(author);
        Article article = articleService.getById(id);
        Integer collects = userinfo.getCollect();
        Integer collect1 = article.getCollect();
        collect1 += 1;
        collects += 1;
        userinfo.setCollect(collects);
        article.setCollect(collect1);
        userinfoService.updateById(userinfo);
        articleService.updateById(article);
        return save;
    }

    @GetMapping("/cancelCollect/{id}/{nickname}/{author}")
    public Boolean cancelCollect(@PathVariable Integer id, @PathVariable String nickname ,@PathVariable String author) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("nickname", nickname);
        boolean remove = collectService.remove(wrapper);
        Userinfo userinfo = userinfoService.queryByNickname(author);
        Integer collect = userinfo.getCollect();
        Article article = articleService.getById(id);
        Integer collect1 = article.getCollect();
        if (collect > 0) {
            collect -= 1;
            userinfo.setCollect(collect);
            userinfoService.updateById(userinfo);
        }
        if (collect1 > 0) {
            collect1 -= 1;
            userinfo.setCollect(collect1);
            articleService.updateById(article);
        }
        return remove;
    }

    @GetMapping("/getCollect/{author}")
    public List<Collect> getCollect(@PathVariable String author) {
        return collectService.getByAuthor(author);
    }
}
