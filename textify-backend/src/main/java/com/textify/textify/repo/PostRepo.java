package com.textify.textify.repo;

import com.textify.textify.entity.Post;
import com.textify.textify.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByIdLessThan(long id, Pageable pageable);

    Page<Post> findByIdLessThanAndUser(long id, User user, Pageable pageable);
}
