package com.textify.textify.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 4, max=255)
    private String username;

    @NotNull
    @Size(min = 4, max=255)
    private String nickname;

    @NotNull
    @Size(min = 8, max=255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;
}

