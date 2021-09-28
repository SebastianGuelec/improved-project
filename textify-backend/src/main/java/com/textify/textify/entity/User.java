package com.textify.textify.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String nickname;

    private String password;
}

