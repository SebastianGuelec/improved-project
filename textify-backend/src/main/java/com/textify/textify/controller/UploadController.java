package com.textify.textify.controller;

import com.textify.textify.entity.FileAttachment;
import com.textify.textify.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/1.0")
public class UploadController {

    @Autowired
    FileService fileService;

    @PostMapping("/posts/upload")
    FileAttachment uploadForHoax(MultipartFile file) {
        return fileService.saveAttachment(file);
    }
}
