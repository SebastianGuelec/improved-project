package com.textify.textify.DTO;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserUpdateDTO {

    @NotNull
    @Size(min=4, max=255)
    private String Nickname;

    private String image;


}
