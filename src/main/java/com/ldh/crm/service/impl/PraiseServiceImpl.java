package com.ldh.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Collect;
import com.ldh.crm.pojo.Praise;
import com.ldh.crm.service.PraiseService;
import com.ldh.crm.mapper.PraiseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 35108
* @description 针对表【praise】的数据库操作Service实现
* @createDate 2023-04-14 21:16:05
*/
@Service
public class PraiseServiceImpl extends ServiceImpl<PraiseMapper, Praise>
    implements PraiseService{

    @Resource
    private PraiseMapper praiseMapper;
    @Override
    public List<Praise> getByNickname(String nickname) {
        return praiseMapper.getByNickname(nickname);
    }

    @Override
    public List<Praise> getByAuthor(String author) {
        return praiseMapper.getByAuthor(author);
    }
}




