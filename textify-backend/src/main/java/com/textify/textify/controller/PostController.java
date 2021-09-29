package com.textify.textify.controller;

import com.textify.textify.DTO.PostDTO;
import com.textify.textify.entity.CurrentUser;
import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.repo.PostRepo;
import com.textify.textify.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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


    @GetMapping({"/posts/{id:[0-9]+}", "/users/{username}/posts/{id:[0-9]+}"})
    ResponseEntity<?> getPostsRelative(@PathVariable long id,@PathVariable(required= false) String username, Pageable pageable,
                                       @RequestParam(name="direction", defaultValue="after") String direction,
                                       @RequestParam(name="count", defaultValue="false", required=false) boolean count
    ) {
        if (!direction.equalsIgnoreCase("after")) {
            return ResponseEntity.ok(postService.getOldPosts(id, username, pageable).map(PostDTO::new));
        }

        if(count == true) {
            long newPostCount = postService.getNewPostsCount(id, username);
            return ResponseEntity.ok(Collections.singletonMap("count", newPostCount));
        }

        List<PostDTO> newPosts = postService.getNewPosts(id,username,pageable).stream()
                .map(PostDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(newPosts);
    }

    }

