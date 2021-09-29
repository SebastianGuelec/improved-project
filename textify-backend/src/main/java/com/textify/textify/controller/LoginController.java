package com.textify.textify.controller;

import com.textify.textify.DTO.UserDTO;
import com.textify.textify.entity.CurrentUser;
import com.textify.textify.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/api/v1/login")
    UserDTO handleLogin(@CurrentUser User loggedInUser) {
        return new UserDTO(loggedInUser);

    }

}
