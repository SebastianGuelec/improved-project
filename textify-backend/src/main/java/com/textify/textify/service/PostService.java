package com.textify.textify.service;

import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.repo.PostRepo;

import java.util.Date;

public class PostService {

    PostRepo postRepo;

    public PostService(PostRepo postRepo) {
        super();
        this.postRepo = postRepo;
    }

    public void save(User user, Post post) {
        post.setTimestamp(new Date());
        post.setUser(user);
        postRepo.save(post);
    }
}
