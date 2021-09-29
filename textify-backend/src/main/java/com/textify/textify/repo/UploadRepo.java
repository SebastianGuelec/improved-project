package com.textify.textify.repo;

import com.textify.textify.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UploadRepo extends JpaRepository<FileAttachment, Long> {
    List<FileAttachment> findByDateBeforeAndPostIsNull(Date date);
}
