package com.textify.textify.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class FileAttachment {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String name;

    private String fileType;

    @OneToOne
    private Post post;
}
