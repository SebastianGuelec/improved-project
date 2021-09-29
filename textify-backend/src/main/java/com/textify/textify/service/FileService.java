package com.textify.textify.service;


import com.textify.textify.config.AppConfig;
import com.textify.textify.entity.FileAttachment;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {

    AppConfig appConfig;

    Tika tika;

    public FileService(AppConfig appConfig) {
        super();
        this.appConfig = appConfig;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileAttachment;
    }
}


