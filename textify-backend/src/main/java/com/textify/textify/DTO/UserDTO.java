package com.textify.textify.DTO;

import com.textify.textify.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Data
    @NoArgsConstructor
    public class UserDTO {
        private long id;

        private String username;

        private String displayName;

        private String image;

        public UserDTO(User user) {
            this.setId(user.getId());
            this.setUsername(user.getUsername());
            this.setDisplayName(user.getNickname());
            this.setImage(user.getImage());
        }

    }

