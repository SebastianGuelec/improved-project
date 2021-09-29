package com.textify.textify.repo;

import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PostRepo extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Page<Post> findByUser(User user, Pageable pageable);

}
