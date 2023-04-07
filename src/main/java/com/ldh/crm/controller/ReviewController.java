package com.ldh.crm.controller;


import cn.hutool.core.date.DateUtil;
import com.ldh.crm.pojo.Review;
import com.ldh.crm.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/getReview/{articleId}")
    public List<Review> getReview(@PathVariable Integer articleId){
        List<Review> list = reviewService.findComment(articleId);
        return list;
    }

    @PostMapping("/sendReview")
    public boolean saveReview(@RequestBody Review review){
        List<Review> list = reviewService.list();
        review.setId(list.size()+1);
        review.setTime(DateUtil.now());
        boolean flag = reviewService.save(review);
        return flag;
    }
}
