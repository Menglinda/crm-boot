package com.ldh.crm.controller;


import cn.hutool.core.date.DateUtil;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.ldh.crm.pojo.Comment;

import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.service.CommentService;
import com.ldh.crm.service.UserinfoService;
import org.json.JSONObject;
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

    private static final String APP_ID = "32779333";
    private static final String API_KEY = "R1FHat0H7DtCU6KFrBAH6Z7j";
    private static final String SECRET_KEY = "XOG281QuhO68ROd8j50DuxnC5vEbiaFm";

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
    public Integer saveComment(@RequestBody Comment comment) {
        String content = comment.getContent();
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        JSONObject response = client.textCensorUserDefined(content);
//            System.out.println(response);
        String conclusion = response.get("conclusion").toString();
        if ("不合规".equals(conclusion)) {
            return -1;
        }
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
        boolean save = commentService.save(comment);
        if (save) {
            return 1;
        }
        return 0;
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
