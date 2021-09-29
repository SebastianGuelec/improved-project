package com.textify.textify.repo;

import com.textify.textify.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepo extends JpaRepository<FileAttachment, Long> {
}
