package com.textify.textify.repo;

import com.textify.textify.entity.User;
import com.textify.textify.view.ProjectUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByUsername(String username);

    @Query("Select u from User u")
    Page<ProjectUser> getAllUsersProjection(Pageable pageable);

    Page<User> findByUsernameNot(String username, Pageable page);
}
