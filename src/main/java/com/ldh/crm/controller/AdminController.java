package com.ldh.crm.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldh.crm.pojo.Admin;
import com.ldh.crm.pojo.Article;
import com.ldh.crm.pojo.R;
import com.ldh.crm.pojo.User;
import com.ldh.crm.service.AdminService;
import com.ldh.crm.service.ArticleService;
import com.ldh.crm.service.UserService;
import com.ldh.crm.vo.ChangePsdInfo;
import com.ldh.crm.vo.PageInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@MapperScan("com.ldh.crm.mapper")
@RequestMapping("/vip")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @PostMapping("/login")
    public R<String> AdminLogin(@RequestBody Admin admin) {
        String res = adminService.adminLogin(admin);
        if (StrUtil.isEmpty(res)) {
            return R.fail("密码错误");
        }
        return R.data(res);
    }

    @PostMapping("/page")
    public IPage<User> findPage(@RequestBody PageInfo pageInfo) {
        IPage<User> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        QueryWrapper<User> queryWarpper = new QueryWrapper<>();
        if (!"".equals(pageInfo.getQuery())) {
            queryWarpper.like("nickname", pageInfo.getQuery());
        }
        return userService.page(page, queryWarpper);
    }

    @PostMapping("/getUser")
    public Page<User> getUser(@RequestBody PageInfo pageInfo) {
        Page<User> page = userService.page(new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize()));
        return page;
    }


    @PostMapping("/add")
    public R<String> addUser(@RequestBody User user) {
        String res = adminService.addUser(user);
        if (StrUtil.isEmpty(res)) {
            return R.fail("用户邮箱已存在！");
        }
        return R.data(res);
    }

    @GetMapping("/edit/{email}")
    public User QueryUsersById(@PathVariable String email) {
        User user = userService.getById(email);
        return user;
    }

    @GetMapping("/change/{email}")
    public Admin QueryAdminById(@PathVariable String email) {
        Admin admin = adminService.getById(email);
        return admin;
    }

    @PostMapping("/change")
    public Boolean ChangePsdById(@RequestBody ChangePsdInfo changePsdInfo) {
        String email = changePsdInfo.getEmail();
        String pass = changePsdInfo.getPass();
        Boolean flag = adminService.updatePsdById(email, pass);
        return flag;
    }

    @PostMapping("/update")
    public boolean UpdateById(@RequestBody User user) {
        boolean flag = userService.updateById(user);
        return flag;
    }

    @DeleteMapping("/remove/{email}")
    public boolean removeUser(@PathVariable String email) {
        boolean flag = userService.removeById(email);
        return flag;
    }

    @PostMapping("/getArticle")
    public IPage<Article> getArticle(@RequestBody PageInfo pageInfo) {
        Page<Article> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        QueryWrapper<Article> queryWarpper = new QueryWrapper<>();
        if (!"".equals(pageInfo.getQuery())) {
            queryWarpper.like("name", pageInfo.getQuery());
        }
        return articleService.page(page, queryWarpper);
    }

    @PostMapping("/addArticle")
    public Integer addArticle(@RequestBody Article article) {
        Integer count=0;
        if (article.getName() != null && article.getType() != null && article.getContent() != null) {
            List<Article> list = articleService.list();
            int size = list.size();
            article.setId(1000+size+1);
            article.setNickname("Admin");
            article.setTime(DateUtil.now());
            count = adminService.addArticle(article);
        }
        return count;
    }

    @GetMapping("/editArticle/{id}")
    public Article queryArticleById(@PathVariable Integer id) {
        Article article = articleService.getById(id);
        return article;
    }

    @PostMapping("/updateArticle")
    public boolean updateArticleById(@RequestBody Article article) {
        boolean flag = articleService.updateById(article);
        return flag;
    }


    @DeleteMapping("/deleteArticle/{id}")
    public boolean deleteArticle(@PathVariable Integer id) {
        boolean flag = articleService.removeById(id);
        return flag;
    }
}
