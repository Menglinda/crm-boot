package com.ldh.crm.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.ldh.crm.pojo.R;
import com.ldh.crm.pojo.User;
import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.pojo.Validation;
import com.ldh.crm.service.UserService;
import com.ldh.crm.service.UserinfoService;
import com.ldh.crm.service.ValidationService;
import com.ldh.crm.vo.ChangePsdUser;
import com.ldh.crm.vo.UserNewPass;
import com.ldh.crm.vo.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserinfoService userinfoService;

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

    @GetMapping("/changePsd/{email}")
    public User queryById(@PathVariable String email) {
        return userService.getById(email);
    }

    @PostMapping("/changePsd")
    public Boolean changePsd(@RequestBody ChangePsdUser changePsdUser) {
        String email = changePsdUser.getEmail();
        String nickname = changePsdUser.getNickname();
        String pass = changePsdUser.getPass();
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(pass);
        return userService.updateById(user);
    }

    @GetMapping("/sendEmail/{email}/{type}")
    public Boolean sendEmail(@PathVariable String email, @PathVariable Integer type) {
        return userService.sendEmail(email, type);
    }

    @PostMapping("/register")
    public String userRegister(@RequestBody UserRegister userRegister) {
        String nickname = userRegister.getNickname();
        List<User> users = userService.selectByNickname(nickname);
        if (users.size() > 0) return "用户名已存在";
        String email = userRegister.getEmail();
        User user = userService.getById(email);
        if (user != null) return "用户邮箱已存在";
        String code = userRegister.getCode();
        QueryWrapper<Validation> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        wrapper.eq("code", code);
        wrapper.ge("time", new Date());
        Validation one = validationService.getOne(wrapper);
        if (one == null) {
            return "请重新发送验证码";
        }
        String password = userRegister.getPassword();
        User user1 = new User();
        user1.setEmail(email);
        user1.setNickname(nickname);
        user1.setPassword(password);
        userService.save(user1);
        Userinfo userinfo = new Userinfo();
        userinfo.setEmail(email);
        userinfo.setNickname(nickname);
        userinfoService.save(userinfo);
        return "注册成功";
    }

    @PostMapping("/setNewPass")
    public String setNewPass(@RequestBody UserNewPass userNewPass) {
        String email = userNewPass.getEmail();
        String password = userNewPass.getPassword();
        String code = userNewPass.getCode();
        User user = userService.getById(email);
        if (user == null) return "该邮箱不存在";
        QueryWrapper<Validation> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        wrapper.eq("code", code);
        wrapper.ge("time", new Date());
        Validation one = validationService.getOne(wrapper);
        if (one == null) {
            return "请重新发送验证码";
        }
        user.setPassword(password);
        userService.updateById(user);
        return "成功";
    }
}
