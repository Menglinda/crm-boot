package com.ldh.crm.service;

import cn.hutool.core.date.DateTime;
import com.ldh.crm.pojo.Validation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 35108
* @description 针对表【validation】的数据库操作Service
* @createDate 2023-04-09 21:11:44
*/
public interface ValidationService extends IService<Validation> {

    void saveCode(String email, String code, Integer type, DateTime offsetMinute);
}
