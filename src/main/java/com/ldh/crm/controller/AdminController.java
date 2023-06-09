package com.ldh.crm.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldh.crm.pojo.*;
import com.ldh.crm.service.*;
import com.ldh.crm.vo.ChangePsdInfo;
import com.ldh.crm.vo.PageInfo;
import com.ldh.crm.vo.UserNewPass;
import org.json.JSONObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@CrossOrigin
@RestController
@MapperScan("com.ldh.crm.mapper")
@RequestMapping("/vip")
public class AdminController {

    private static final String APP_ID = "32779333";
    private static final String API_KEY = "R1FHat0H7DtCU6KFrBAH6Z7j";
    private static final String SECRET_KEY = "XOG281QuhO68ROd8j50DuxnC5vEbiaFm";
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private CommentService commentService;

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
        if (res.equals("邮箱已存在")) {
            return R.fail("用户邮箱已存在！");
        } else if (res.equals("用户名已存在")) {
            return R.fail("用户名已存在！");
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

    @DeleteMapping("/deleteReview/{id}")
    public boolean removeReview(@PathVariable Integer id) {
        boolean flag = reviewService.removeById(id);
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
            article.setNickname("Admin");
            article.setTime(DateUtil.now());
            article.setToday(DateUtil.today());
            String nickname = article.getNickname();
            Userinfo userinfo = userinfoService.queryByNickname(nickname);
            Integer article1 = userinfo.getArticle();
            article1 += 1;
            userinfo.setArticle(article1);
            userinfoService.updateById(userinfo);
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
    public Integer updateArticleById(@RequestBody Article article) {
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
        boolean flag = articleService.updateById(article);
        if (flag) {
            return 1;
        }
        return 0;
    }


    @DeleteMapping("/deleteArticle/{id}")
    public boolean deleteArticle(@PathVariable Integer id) {
        boolean flag = articleService.removeById(id);
        if (flag) {
            QueryWrapper<Collect> wrapper = new QueryWrapper<>();
            wrapper.eq("article_id", id);
            List<Collect> list = collectService.list(wrapper);
            int size = list.size();
            String author = null;
            for (Collect collect : list) {
                Integer id1 = collect.getId();
                collectService.removeById(id1);
                author = collect.getAuthor();
            }
            QueryWrapper<Praise> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("article_id", id);
            List<Praise> list1 = praiseService.list(wrapper1);
            int size1 = list1.size();
            for (Praise praise : list1) {
                Integer id1 = praise.getId();
                praiseService.removeById(id1);
            }
            Userinfo userinfo = userinfoService.queryByNickname(author);
            Integer collect = userinfo.getCollect();
            Integer praise = userinfo.getPraise();
            Integer article = userinfo.getArticle();
            collect -= size;
            praise -= size1;
            article -= 1;
            userinfo.setCollect(collect);
            userinfo.setPraise(praise);
            userinfo.setArticle(article);
            userinfoService.updateById(userinfo);
            QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("article_id",id);
            List<Comment> list2 = commentService.list(wrapper2);
            for (Comment comment : list2) {
                commentService.removeById(comment);
            }
        }
        return flag;
    }

    @GetMapping("/getAllArticle")
    public List<Article> getAll() {
        List<Article> list = articleService.list();
        return list;
    }

    @PostMapping("/setNewPass")
    public String setNewPass(@RequestBody UserNewPass userNewPass) {
        String email = userNewPass.getEmail();
        String password = userNewPass.getPassword();
        String code = userNewPass.getCode();
        Admin admin = adminService.getById(email);
        if (admin == null) return "该邮箱不存在";
        QueryWrapper<Validation> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        wrapper.eq("code", code);
        wrapper.ge("time", new Date());
        Validation one = validationService.getOne(wrapper);
        if (one == null) {
            return "请重新发送验证码";
        }
        admin.setPassword(password);
        adminService.updateById(admin);
        return "成功";
    }
}
