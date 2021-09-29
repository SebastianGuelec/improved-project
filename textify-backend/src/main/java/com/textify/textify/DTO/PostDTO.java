package com.textify.textify.DTO;

import com.textify.textify.entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDTO {
    private long id;

    private String content;

    private long date;

    private UserDTO user;

    public PostDTO(Post post) {
        this.setId(post.getId());
        this.setContent(post.getContent());
        this.setDate(post.getTimestamp().getTime());
        this.setUser(new UserDTO(post.getUser()));
    }


}
