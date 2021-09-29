package com.textify.textify.service;

import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.repo.PostRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public class PostService {

    PostRepo postRepo;

    UserService userService;

    public PostService(PostRepo postRepo, UserService userService) {
        super();
        this.postRepo = postRepo;
        this.userService = userService;
    }

    public Post save(User user, Post post) {
        post.setTimestamp(new Date());
        post.setUser(user);
        return postRepo.save(post);
    }

    public Page<Post> getAllPosts(Pageable pageable){
        return postRepo.findAll(pageable);
    }
    public Page<Post> getPostsOfUser(String username, Pageable pageable) {
        User inDB = userService.getByUsername(username);
        return postRepo.findByUser(inDB, pageable);
    }
}
