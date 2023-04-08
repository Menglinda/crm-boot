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
    public List<Review> getReview(@PathVariable Integer articleId) {
        return reviewService.getReview(articleId);
    }

    @PostMapping("/sendReview")
    public boolean saveReview(@RequestBody Review review) {
        review.setTime(DateUtil.now());
        boolean flag = reviewService.save(review);
        return flag;
    }


}
