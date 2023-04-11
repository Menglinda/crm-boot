package com.ldh.crm.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ldh.crm.pojo.Files;
import com.ldh.crm.service.FilesService;
import com.ldh.crm.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FilesController {

    @Value("${files.upload.path}")
    private String uploadPath;

    @Autowired
    private FilesService filesService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file,@RequestParam String nickname) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        if (originalFilename.contains(".")) {
            originalFilename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        }
        long size = file.getSize();
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File up = new File(uploadPath + fileUUID);
        file.transferTo(up);
        String url = "http://localhost:8888/file/" + fileUUID;
        Files files = new Files();
        files.setName(originalFilename);
        files.setType(type);
        files.setSize(size / 1024);
        files.setUrl(url);
        files.setTime(DateUtil.now());
        files.setNickname(nickname);
        filesService.save(files);
        return url;

    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File up = new File(uploadPath + fileUUID);
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");
        outputStream.write(FileUtil.readBytes(up));
        outputStream.flush();
        outputStream.close();
    }

    @PostMapping("/getFiles")
    public IPage<Files> getFiles(@RequestBody PageInfo pageInfo) {
        Page<Files> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        QueryWrapper<Files> queryWarpper = new QueryWrapper<>();
        if (!"".equals(pageInfo.getQuery())) {
            queryWarpper.like("name", pageInfo.getQuery());
        }
        return filesService.page(page, queryWarpper);
    }

    @DeleteMapping("/removeFile/{id}")
    public boolean removeFile(@PathVariable Integer id) {
        return filesService.removeById(id);
    }

}
