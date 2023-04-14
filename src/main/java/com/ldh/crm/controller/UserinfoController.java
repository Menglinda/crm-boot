package com.ldh.crm.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ldh.crm.pojo.*;
import com.ldh.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RequestMapping("/info")
@RestController
public class UserinfoController {

    @Value("${files.upload.path}")
    private String uploadPath;

    @Resource
    private UserinfoService userinfoService;

    @Resource
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private PraiseService praiseService;


    @PostMapping("/uploadAvatar")
    String uploadAvatar(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File up = new File(uploadPath + fileUUID);
        file.transferTo(up);
        String url = "http://localhost:8888/file/" + fileUUID;
        return url;
    }

    @GetMapping("/getInfo/{nickname}")
    public Userinfo getUserInfo(@PathVariable String nickname) {
        return userinfoService.queryByNickname(nickname);
    }

    @PostMapping("/editInfo")
    public boolean editInfo(@RequestBody Userinfo userinfo) {
        String email = userinfo.getEmail();
        String nickname = userinfo.getNickname();
        User user = userService.getById(email);
        user.setNickname(nickname);
        userService.updateById(user);
        return userinfoService.updateById(userinfo);
    }

    @GetMapping("/getArticle/{nickname}")
    public List<Article> getArticle(@PathVariable String nickname) {
        return articleService.getByNickname(nickname);
    }

    @GetMapping("/getCollectArticle/{nickname}")
    public List<Article> getCollectArticle(@PathVariable String nickname) {
        List<Collect> list = collectService.getByNickname(nickname);
        ArrayList<Article> articles = new ArrayList<Article>();
        for (Collect collect : list) {
            Integer id = collect.getId();
            Article article = articleService.getById(id);
            articles.add(article);
        }
        if (articles == null && articles.size() == 0) {
            return null;
        } else {
            return articles;
        }
    }

    @GetMapping("/getPraiseArticle/{nickname}")
    public List<Article> getPraiseArticle(@PathVariable String nickname) {
        List<Praise> list = praiseService.getByNickname(nickname);
        ArrayList<Article> articles = new ArrayList<>();
        for (Praise praise : list) {
            Integer id = praise.getId();
            Article article = articleService.getById(id);
            articles.add(article);
        }
        if (articles == null && articles.size() == 0) {
            return null;
        } else {
            return articles;
        }
    }
}
