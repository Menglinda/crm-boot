package com.ldh.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldh.crm.pojo.Article;
import com.ldh.crm.pojo.Collect;
import com.ldh.crm.pojo.Praise;
import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.service.ArticleService;
import com.ldh.crm.service.PraiseService;
import com.ldh.crm.service.UserService;
import com.ldh.crm.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/praise")
public class PraiseController {

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private ArticleService articleService;


    @GetMapping("/praiseArticle/{id}/{name}/{nickname}")
    public Boolean praiseArticle(@PathVariable Integer id,  @PathVariable String name,@PathVariable String nickname) {
        Praise praise = new Praise();
        praise.setId(id);
        praise.setNickname(name);
        boolean save = praiseService.save(praise);
        Userinfo userinfo = userinfoService.queryByNickname(nickname);
        Article article = articleService.getById(id);
        Integer praise1 = article.getPraise();
        praise1 += 1;
        article.setPraise(praise1);
        articleService.updateById(article);
        Integer praises = userinfo.getPraise();
        praises += 1;
        userinfo.setPraise(praises);
        userinfoService.updateById(userinfo);
        return save;
    }

    @GetMapping("/cancelPraise/{id}/{name}/{nickname}")
    public Boolean cancelPraise(@PathVariable Integer id, @PathVariable String name, @PathVariable String nickname) {
        QueryWrapper<Praise> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        wrapper.eq("nickname", name);
        boolean remove = praiseService.remove(wrapper);
        Userinfo userinfo = userinfoService.queryByNickname(nickname);
        Article article = articleService.getById(id);
        Integer praise1 = article.getPraise();
        Integer praise = 0;
        if (userinfo!=null){
            praise= userinfo.getPraise();
        }
        if (praise > 0) {
            praise -= 1;
            userinfo.setPraise(praise);
            userinfoService.updateById(userinfo);
        }
        if (praise1 > 0) {
            praise1 -= 1;
            article.setPraise(praise1);
            articleService.updateById(article);
        }
        return remove;
    }

}
