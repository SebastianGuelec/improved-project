package com.textify.textify.service;

import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import com.textify.textify.repo.PostRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostSecurityService {

    PostRepo postRepo;

    public PostSecurityService(PostRepo postRepo) {
        super();
        this.postRepo = postRepo;
    }

    public boolean isAllowedToDelete(long postId, User loggedInUser) {
        Optional<Post> optionalPost = postRepo.findById(postId);
        if(optionalPost.isPresent()) {
            Post inDB = optionalPost.get();
            return inDB.getUser().getId() == loggedInUser.getId();
        }
        return false;
    }
}
