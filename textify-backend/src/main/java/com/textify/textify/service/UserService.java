package com.textify.textify.service;

import com.textify.textify.entity.User;
import com.textify.textify.errorHandling.NotFoundException;
import com.textify.textify.repo.UserRepo;
import com.textify.textify.DTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {

    UserRepo userRepo;

    BCryptPasswordEncoder passwordEncoder;
    FileService fileService;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, FileService fileService) {
        super();
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.fileService = fileService;
    }
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);

    }
    public Page<User> getUsers(User loggedInUser, Pageable pageable) {
        if(loggedInUser != null) {
            return userRepo.findByUsernameNot(loggedInUser.getUsername(), pageable);
        }
        return userRepo.findAll(pageable);
    }
    public User getByUsername(String username) {
        User inDB = userRepo.findByUsername(username);
        if(inDB == null) {
            throw new NotFoundException(username + " not found");
        }

        return inDB;
    }
    public User update(long id, UserUpdateDTO userUpdate) {
        User inDB = userRepo.getOne(id);
        inDB.setNickname(userUpdate.getNickname());
        if(userUpdate.getImage() != null) {
            String savedImageName;
            try {
                savedImageName = fileService.saveProfileImage(userUpdate.getImage());
                fileService.deleteProfileImage(inDB.getImage());
                inDB.setImage(savedImageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userRepo.save(inDB);
    }
}

