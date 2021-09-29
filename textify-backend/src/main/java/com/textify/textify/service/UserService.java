package com.textify.textify.service;

import com.textify.textify.entity.User;
import com.textify.textify.repo.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepo userRepo;

    BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo){
        super();
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);

    }
    public Page<User> getUsers(Pageable page) {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepo.findAll(pageable);
    }
}
