package com.ldh.crm.controller;


import cn.hutool.core.date.DateUtil;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldh.crm.pojo.Article;
import com.ldh.crm.pojo.Collect;
import com.ldh.crm.pojo.Praise;
import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.service.ArticleService;
import com.ldh.crm.service.CollectService;
import com.ldh.crm.service.PraiseService;
import com.ldh.crm.service.UserinfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private CollectService collectService;


    @Autowired
    private PraiseService praiseService;

    private static final String APP_ID = "32779333";
    private static final String API_KEY = "R1FHat0H7DtCU6KFrBAH6Z7j";
    private static final String SECRET_KEY = "XOG281QuhO68ROd8j50DuxnC5vEbiaFm";

    @GetMapping("/test/{text}")
    public void testText(@PathVariable String text) {
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        JSONObject response = client.textCensorUserDefined(text);
        System.out.println(response);
        String conclusion = response.get("conclusion").toString();
        System.out.println(conclusion);
    }

    @PostMapping("/addArticleByUser")
    public Integer addArticleByUser(@RequestBody Article article) {
        Integer count = 0;
        if (article.getName() != null && article.getType() != null && article.getContent() != null) {
            String content = article.getContent();
            String name = article.getName();
            AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
            JSONObject response = client.textCensorUserDefined(content);
            JSONObject jsonObject = client.textCensorUserDefined(name);
//            System.out.println(response);
            String conclusion = response.get("conclusion").toString();
            String conclusion1 = jsonObject.get("conclusion").toString();
            if ("不合规".equals(conclusion) || "不合规".equals(conclusion1)) {
                return -1;
            }
            String nickname = article.getNickname();
            String today = DateUtil.today();
            article.setTime(DateUtil.now());
            article.setToday(today);
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.eq("nickname", nickname);
            wrapper.eq("today", today);
            List<Article> list = articleService.list(wrapper);
            Userinfo userinfo = null;
            userinfo = userinfoService.queryByNickname(nickname);
            if (list.size() == 0) {
                Integer points = userinfo.getPoints();
                points += 5;
                userinfo.setPoints(points);
                userinfoService.updateById(userinfo);
                count++;
            }
            Integer article1 = userinfo.getArticle();
            article1 += 1;
            userinfo.setArticle(article1);
            userinfoService.updateById(userinfo);
            articleService.addArticle(article);
            count++;

        }
        return count;
    }

    @GetMapping("/getArticle/{id}")
    public Article getArticleById(@PathVariable Integer id) {
        Article article = articleService.getById(id);
        return article;
    }

    @GetMapping("/isCollect/{id}/{nickname}")
    public Integer isCollect(@PathVariable Integer id, @PathVariable String nickname) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        wrapper.eq("nickname", nickname);
        Collect one = collectService.getOne(wrapper);
        if (one == null) return 0;
        else return 1;
    }

    @GetMapping("/isPraise/{id}/{nickname}")
    public Integer isPraise(@PathVariable Integer id, @PathVariable String nickname) {
        QueryWrapper<Praise> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        wrapper.eq("nickname", nickname);
        Praise one = praiseService.getOne(wrapper);
        if (one == null) return 0;
        else return 1;
    }

    @GetMapping("/getMostHot")
    public List<Article> getMostHot() {
        return articleService.getMostHot();
    }

    @GetMapping("/getMostNew")
    public List<Article> getMostNew() {
        return articleService.getMostNew();
    }
}
