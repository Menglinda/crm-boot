package com.ldh.crm.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Admin;
import com.ldh.crm.pojo.User;
import com.ldh.crm.pojo.Validation;
import com.ldh.crm.resultEnum.ValidationEnum;
import com.ldh.crm.service.UserService;
import com.ldh.crm.mapper.UserMapper;
import com.ldh.crm.service.ValidationService;
import com.ldh.crm.vo.PageInfo;
import com.ldh.crm.vo.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.ServiceUnavailableException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * @author 35108
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-04-01 15:05:16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private ValidationService validationService;


    @Override
    public List<User> selectByNickname(String nickname) {
        List<User> users = userMapper.selectByNickname(nickname);
        return users;
    }

    @Override
    public Boolean sendEmail(String email,Integer type) {

        Date now = new Date();
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email",email);
        validationQueryWrapper.eq("type", type);
        validationQueryWrapper.ge("time", now);
        Validation validation = validationService.getOne(validationQueryWrapper);
        if(validation!=null){
            return false;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("liudaihua68@163.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSentDate(now);
        String code = RandomUtil.randomNumbers(6);
        simpleMailMessage.setSubject("【考研助力服务平台】");
        if (ValidationEnum.REGISTER.getCode().equals(type)) {
            simpleMailMessage.setText("账号注册----本次邮箱验证码为：" + code + "，有效期三分钟。请妥善保管，切勿泄露！");
        }else if(ValidationEnum.FORGET_PASS.getCode().equals(type)){
            simpleMailMessage.setText("忘记密码----本次邮箱验证码为：" + code + "，有效期三分钟。请妥善保管，切勿泄露！");
        }

        javaMailSender.send(simpleMailMessage);
        validationService.saveCode(email, code, type, DateUtil.offsetMinute(now, 3));

        return true;
    }


}




