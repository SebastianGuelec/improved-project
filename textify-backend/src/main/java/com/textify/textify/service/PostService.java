package com.textify.textify.service;

import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.repo.PostRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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

    public Page<Post> getOldPosts(long id, String username, Pageable pageable) {
        Specification<Post> spec = Specification.where(idLessThan(id));
        if(username != null) {
            User inDB = userService.getByUsername(username);
            spec = spec.and(userIs(inDB));
        }
        return postRepo.findAll(spec, pageable);
    }

    public List<Post> getNewPosts(long id, String username, Pageable pageable) {
        Specification<Post> spec = Specification.where(idGreaterThan(id));
        if(username != null) {
            User inDB = userService.getByUsername(username);
            spec = spec.and(userIs(inDB));
        }
        return postRepo.findAll(spec, pageable.getSort());
    }


    public long getNewPostsCount(long id, String username) {
        Specification<Post> spec = Specification.where(idGreaterThan(id));
        if(username != null) {
            User inDB = userService.getByUsername(username);
            spec = spec.and(userIs(inDB));
        }
        return postRepo.count(spec);
    }

    private Specification<Post> userIs(User user){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }

    private Specification<Post> idLessThan(long id){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThan(root.get("id"), id);
        };
    }

    private Specification<Post> idGreaterThan(long id){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get("id"), id);
        };
    }
    }
