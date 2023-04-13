package com.ldh.crm.controller;


import cn.hutool.core.date.DateUtil;
import com.ldh.crm.pojo.Comment;

import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.service.CommentService;
import com.ldh.crm.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserinfoService userinfoService;

    @GetMapping("/getComment/{articleId}")
    public List<Comment> getComment(@PathVariable Integer articleId) {
        List<Comment> comments = commentService.findComment(articleId);
        List<Comment> originList = comments.stream().filter(comment -> comment.getOriginId() == null).collect(Collectors.toList());
        for (Comment origin : originList) {
            List<Comment> children = comments.stream().filter(comment -> origin.getId().equals(comment.getOriginId())).collect(Collectors.toList());
            children.forEach(comment -> {
                comments.stream().filter(c1 -> c1.getId().equals(comment.getPid())).findFirst().ifPresent(v -> {
                    comment.setPUserId(v.getUserId());
                    comment.setPNickname(v.getNickname());
                });
            });
            origin.setChildren(children);
        }
        return originList;
    }

    @PostMapping("/sendComment")
    public boolean saveComment(@RequestBody Comment comment) {
        comment.setTime(DateUtil.now());
        if (comment.getPid() != null) {
            Integer pid = comment.getPid();
            Comment pComment = commentService.getById(pid);
            if (pComment.getOriginId() != null) {
                comment.setOriginId(pComment.getOriginId());
            } else {
                comment.setOriginId(comment.getPid());
            }
        }
        boolean flag = commentService.save(comment);
        return flag;
    }

    @GetMapping("/getInfo/{nickname}")
    public Userinfo getInfo(@PathVariable String nickname) {
        Userinfo userinfo = userinfoService.queryByNickname(nickname);
        return userinfo;
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeComment(@PathVariable Integer id) {
        boolean flag = commentService.removeById(id);
        return flag;
    }

}
