package com.textify.textify.controller;

import com.textify.textify.entity.CurrentUser;
import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/posts")
    void createPost(@Valid @RequestBody Post post, @CurrentUser User user) {
    postService.save(user, post);
    }
}
