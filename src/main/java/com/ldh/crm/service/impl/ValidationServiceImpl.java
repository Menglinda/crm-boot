package com.ldh.crm.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Validation;
import com.ldh.crm.resultEnum.ValidationEnum;
import com.ldh.crm.service.ValidationService;
import com.ldh.crm.mapper.ValidationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 35108
 * @description 针对表【validation】的数据库操作Service实现
 * @createDate 2023-04-09 21:11:44
 */
@Service
public class ValidationServiceImpl extends ServiceImpl<ValidationMapper, Validation>
        implements ValidationService {

    @Transactional
    @Override
    public void saveCode(String email, String code, Integer type, DateTime offsetMinute) {
        Validation validation = new Validation();
        validation.setEmail(email);
        validation.setCode(code);
        validation.setType(type);
        validation.setTime(offsetMinute);

        // 删除同类型的验证
        UpdateWrapper<Validation> wrapper = new UpdateWrapper<>();
        wrapper.eq("email", email);
        wrapper.eq("type", type);
        remove(wrapper);

        //再插入新的code
        save(validation);
    }
}




