package com.textify.textify.service;

import com.textify.textify.entity.FileAttachment;
import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.repo.PostRepo;
import com.textify.textify.repo.UploadRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    PostRepo postRepo;

    UserService userService;

    UploadRepo uploadRepo;

    FileService fileService;

    public PostService(PostRepo postRepo, UserService userService,UploadRepo uploadRepo , FileService fileService ) {
        super();
        this.postRepo = postRepo;
        this.userService = userService;
        this.uploadRepo = uploadRepo;
        this.fileService = fileService;
    }

    public Post save(User user, Post post) {
        post.setTimestamp(new Date());
        post.setUser(user);
        if(post.getAttachment() != null) {
            FileAttachment inDB = uploadRepo.findById(post.getAttachment().getId()).get();
            inDB.setPost(post);
            post.setAttachment(inDB);
        }
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
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }

    private Specification<Post> idLessThan(long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"), id);
    }

    private Specification<Post> idGreaterThan(long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), id);
    }
    public void deletePost(long id) {
        Post post = postRepo.getOne(id);
        if(post.getAttachment() != null) {
            fileService.deleteAttachmentImage(post.getAttachment().getName());
        }
        postRepo.deleteById(id);

    }
    }
