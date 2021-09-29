package com.textify.textify.controller;


import com.textify.textify.DTO.UserDTO;
import com.textify.textify.DTO.UserUpdateDTO;
import com.textify.textify.entity.CurrentUser;
import com.textify.textify.entity.User;
import com.textify.textify.errorHandling.ApiError;
import com.textify.textify.errorHandling.GenericResponse;
import com.textify.textify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    GenericResponse createUser(@RequestBody User user) {
        userService.save(user);
        return new GenericResponse("User saved.");
    }

    @GetMapping("/users")
    Page<UserDTO> getUsers(@CurrentUser User loggedInUser, Pageable page) {
        return userService.getUsers(loggedInUser, page).map(UserDTO::new);
    }

    @GetMapping("/users/{username}")
    UserDTO getUserByName(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return new UserDTO(user);
    }

    @PutMapping("/users/{id:[0-9]+}")
    @PreAuthorize("#id == principal.id")
    UserDTO updateUser(@PathVariable long id, @Valid , @RequestBody(required = false) UserUpdateDTO userUpdate) {
        User updated = userService.update(id, userUpdate);
        return new UserDTO(updated);
    }
}