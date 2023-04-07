package com.ldh.crm.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.ldh.crm.pojo.R;
import com.ldh.crm.pojo.User;
import com.ldh.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<String> userLogin(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", user.getEmail());
        queryWrapper.eq("password", user.getPassword());
        List<User> list = userService.list(queryWrapper);
        String res = null;
        if (list != null && list.size() > 0) {
            res = IdUtil.simpleUUID();
        }
        if (StrUtil.isEmpty(res)) {
            return R.fail("用户邮箱或密码错误！");
        }
        return R.data(res);
    }

    @PostMapping("/getUser")
    public User getUser(@RequestBody User user) {
        String email = user.getEmail();
        return userService.getById(email);
    }
}
