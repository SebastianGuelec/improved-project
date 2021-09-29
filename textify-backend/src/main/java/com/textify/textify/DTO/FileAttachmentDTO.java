package com.textify.textify.DTO;

import com.textify.textify.entity.FileAttachment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileAttachmentDTO {
    private String name;

    private String fileType;

    public FileAttachmentDTO(FileAttachment fileAttachment) {
        this.setName(fileAttachment.getName());
        this.setFileType(fileAttachment.getFileType());
    }
}
