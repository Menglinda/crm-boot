package com.ldh.crm.service;

import com.ldh.crm.pojo.Review;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 35108
* @description 针对表【review】的数据库操作Service
* @createDate 2023-04-08 10:49:17
*/
public interface ReviewService extends IService<Review> {

    List<Review> getReview(Integer articleId);
}
