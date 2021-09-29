package com.textify.textify;

import com.textify.textify.entity.User;
import com.textify.textify.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@SpringBootApplication
public class TextifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextifyApplication.class, args);
    }
    @Bean
    @Profile("!test")
    CommandLineRunner run(UserService userService) {
        return (args) -> {
            IntStream.rangeClosed(1,15)
                    .mapToObj(i -> {
                        User user = new User();
                        user.setUsername("user"+i);
                        user.setNickname("display"+i);
                        user.setPassword("P4ssword");
                        return user;
                    })
                    .forEach(userService::save);

        };
    }
}
