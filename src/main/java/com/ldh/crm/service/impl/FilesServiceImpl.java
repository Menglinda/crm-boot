package com.ldh.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Files;
import com.ldh.crm.service.FilesService;
import com.ldh.crm.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 35108
 * @description 针对表【files】的数据库操作Service实现
 * @createDate 2023-04-11 20:17:36
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files>
        implements FilesService {

    @Resource
    private FilesMapper filesMapper;

}




