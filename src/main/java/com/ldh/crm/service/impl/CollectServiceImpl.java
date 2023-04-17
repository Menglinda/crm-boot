package com.ldh.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Article;
import com.ldh.crm.pojo.Collect;
import com.ldh.crm.service.CollectService;
import com.ldh.crm.mapper.CollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 35108
* @description 针对表【collect】的数据库操作Service实现
* @createDate 2023-04-14 20:17:35
*/
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
    implements CollectService{

    @Resource
    private CollectMapper collectMapper;
    @Override
    public List<Collect> getByNickname(String nickname) {
        return collectMapper.getByNickname(nickname);
    }

    @Override
    public List<Collect> getByAuthor(String author) {
        return collectMapper.getByAuthor(author);
    }
}




