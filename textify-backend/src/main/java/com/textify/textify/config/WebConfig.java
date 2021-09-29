package com.textify.textify.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AppConfig appConfig;

    @Bean
    CommandLineRunner createUploadFolder() {
        return (args) -> {

            createNonExistingFolder(appConfig.getUploadPath());
            createNonExistingFolder(appConfig.getFullProfileImagesPath());
            createNonExistingFolder(appConfig.getFullAttachmentsPath());
            };
        }
    private void createNonExistingFolder(String path) {
        File folder = new File(path);
        boolean folderExist = folder.exists() && folder.isDirectory();
        if(!folderExist) {
            folder.mkdir();
        }
    }
    }

