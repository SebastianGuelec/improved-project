package com.textify.textify.entity;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Size(min = 10, max=5000)
    @Column(length = 5000)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;


    @ManyToOne
    private User user;
}
