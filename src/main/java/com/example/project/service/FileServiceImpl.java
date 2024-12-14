package com.example.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        //File name of the user uploaded image
        String originalFilename = image.getOriginalFilename();

        // Generate a unique file name
        // mat.jpg --> 1234 --> 1234.jpg
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String filePath = path + File.separator + fileName;
        // Check if path exist and if not create
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdirs();

        // Upload to server
        Files.copy(image.getInputStream(), Paths.get(filePath));

        //return the filename
        return fileName;
    }
}
