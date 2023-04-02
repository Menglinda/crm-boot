package com.ldh.crm.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ldh.crm.pojo.Admin;
import com.ldh.crm.pojo.R;
import com.ldh.crm.pojo.User;
import com.ldh.crm.service.AdminService;
import com.ldh.crm.service.UserService;
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

    @PostMapping("/login")
    public R<String> AdminLogin(@RequestBody Admin admin) {
        String res = adminService.adminLogin(admin);
        if(StrUtil.isEmpty(res)){
            return R.fail("密码错误");
        }
        return R.data(res);
    }

    @GetMapping("/users")
    public List<User> QueryUsers() {
        List<User> users = userService.list();
        return users;
    }

    @PostMapping("/add")
    public R<String> addUser(@RequestBody User user) {
        String res = adminService.addUser(user);
        if(StrUtil.isEmpty(res)){
            return R.fail("用户邮箱已存在！");
        }
        return R.data(res);
    }

    @GetMapping("/edit/{email}")
    public User QueryUsersById(@PathVariable String email) {
        User user = userService.getById(email);
        return user;
    }

    @PostMapping("/update")
    public boolean UpdateById(@RequestBody User user) {
        boolean flag = userService.updateById(user);
        return flag;
    }
}
