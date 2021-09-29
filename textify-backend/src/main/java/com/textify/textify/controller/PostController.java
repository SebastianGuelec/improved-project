package com.textify.textify.controller;

import com.textify.textify.DTO.PostDTO;
import com.textify.textify.entity.CurrentUser;
import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/posts")
    PostDTO createPost(@Valid @RequestBody Post post, @CurrentUser User user) {
    return new PostDTO(user, post);
    }

    @GetMapping("/posts")
    Page<PostDTO> getAllHoaxes(Pageable pageable) {
        return postService.getAllPosts(pageable).map(PostDTO::new);
    }
    @GetMapping("/users/{username}/posts")
    Page<PostDTO> getPostsOfUser(@PathVariable String username, Pageable pageable) {
        return postService.getPostsOfUser(username, pageable).map(PostDTO::new);

    }


    @GetMapping("/posts/{id:[0-9]+}")
    Page<?> getPostsRelative(@PathVariable long id, Pageable pageable) {
        return PostService.getOldPosts(id, pageable).map(PostDTO::new);
    }


    @GetMapping("/users/{username}/posts/{id:[0-9]+}")
    Page<?> getPostsRelativeForUser(@PathVariable String username, @PathVariable long id, Pageable pageable) {
        return PostService.getOldPostsOfUser(id, username, pageable).map(PostDTO::new);

    }
}
