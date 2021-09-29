package com.textify.textify.service;

import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.repo.PostRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

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

    public Page<Post> getOldPosts(long id, Pageable pageable){
       return postRepo.findByIdLessThan(id,pageable);
    }
    public Page<Post> getOldPostsOfUser(long id, String username, Pageable pageable) {
        User inDB = userService.getByUsername(username);
        return postRepo.findByIdLessThanAndUser(id, inDB, pageable);
    }
    public List<Post> getNewPosts(long id, Pageable pageable) {
        return postRepo.findByGreaterThan(id, pageable.getSort());
    }
    public List<Post> getNewPostsOfUser(long id, String username, Pageable pageable) {
        User inDB = userService.getByUsername(username);
        return postRepo.findByIdGreaterThanAndUser(id, inDB, pageable.getSort());
    }

    public long getNewPostsCount(long id) {
        return postRepo.countByIdGreaterThan(id);
    }

    public long getNewPostsCountOfUser(long id, String username) {
        User inDB = userService.getByUsername(username);
        return postRepo.countByIdGreaterThanAndUser(id, inDB);
    }
}
