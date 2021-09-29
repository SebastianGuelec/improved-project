package com.textify.textify.service;


import com.textify.textify.config.AppConfig;
import com.textify.textify.entity.FileAttachment;
import com.textify.textify.repo.UploadRepo;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class FileService {

    AppConfig appConfig;

    Tika tika;

    UploadRepo uploadRepo;

    public FileService(AppConfig appConfig, UploadRepo uploadRepo) {
        super();
        this.appConfig = appConfig;
        this.uploadRepo = uploadRepo;
        tika = new Tika();
    }

    public String saveProfileImage(String base64Image) throws IOException {
        String imageName = getRandomName();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
        File target = new File(appConfig.getFullProfileImagesPath() + "/" + imageName);
        FileUtils.writeByteArrayToFile(target, decodedBytes);
        return imageName;
    }
    private String getRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String detectType(byte[] fileArr) {
        return tika.detect(fileArr);
    }
    public void deleteProfileImage(String image) {
        try {
            Files.deleteIfExists(Paths.get(appConfig.getFullProfileImagesPath()+"/"+image));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public FileAttachment saveAttachment(MultipartFile file) {
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setDate(new Date());
        String randomName = getRandomName();
        fileAttachment.setName(randomName);

        File target = new File(appConfig.getFullAttachmentsPath() +"/"+randomName);
        try {
            byte[] fileAsByte = file.getBytes();
            FileUtils.writeByteArrayToFile(target, fileAsByte);
            fileAttachment.setFileType(detectType(fileAsByte));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadRepo.save(fileAttachment);
    }
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void cleanupStorage() {
        Date oneHourAgo = new Date(System.currentTimeMillis() - (60*60*1000));
        List<FileAttachment> oldFiles = uploadRepo.findByDateBeforeAndPostIsNull(oneHourAgo);
        for(FileAttachment file: oldFiles) {
            deleteAttachmentImage(file.getName());
            uploadRepo.deleteById(file.getId());
        }

    }

    public void deleteAttachmentImage(String image) {
        try {
            Files.deleteIfExists(Paths.get(appConfig.getFullAttachmentsPath()+"/"+image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


