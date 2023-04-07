package com.ldh.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.pojo.Review;
import com.ldh.crm.service.ReviewService;
import com.ldh.crm.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 35108
* @description 针对表【review】的数据库操作Service实现
* @createDate 2023-04-07 17:59:42
*/
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
    implements ReviewService{
    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public List<Review> findComment(Integer articleId) {


        return  reviewMapper.findComment(articleId);
    }

}




