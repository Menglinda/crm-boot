package com.ldh.crm.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ldh.crm.mapper.UserinfoMapper;
import com.ldh.crm.pojo.User;
import com.ldh.crm.pojo.Userinfo;
import com.ldh.crm.service.UserService;
import com.ldh.crm.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@CrossOrigin
@RequestMapping("/info")
@RestController
public class UserinfoController {

    @Value("${files.upload.path}")
    private String uploadPath;

    @Resource
    private UserinfoService userinfoService;

    @Autowired
    private UserService userService;

    @PostMapping("/uploadAvatar")
    String uploadAvatar(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File up = new File(uploadPath + fileUUID);
        file.transferTo(up);
        String url = "http://localhost:8888/file/" + fileUUID;
        return url;
    }

    @GetMapping("/getInfo/{nickname}")
    public Userinfo getUserInfo(@PathVariable String nickname) {
        return userinfoService.queryByNickname(nickname);
    }

    @PostMapping("/editInfo")
    public boolean editInfo(@RequestBody Userinfo userinfo){
        String email = userinfo.getEmail();
        String nickname = userinfo.getNickname();
        User user = userService.getById(email);
        user.setNickname(nickname);
        userService.updateById(user);
        return userinfoService.updateById(userinfo);
    }
}
