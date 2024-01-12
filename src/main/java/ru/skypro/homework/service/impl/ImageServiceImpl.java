package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.EntityWithImage;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final String imageDirectory;

    public ImageServiceImpl(@Value("${image-directory}") String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    @Override
    public String saveImage(MultipartFile image, EntityWithImage entity) throws IOException {
        String subPath = entity.getClass().getSimpleName();
        Path path;
        if(entity.getImageUrl()  != null && !entity.getImageUrl().isBlank()) {
            Files.deleteIfExists(Path.of(imageDirectory, subPath, stripUrl(entity.getImageUrl())));
        }
        path = writeImageToFile(image, subPath);
        return path.getFileName().toString();
    }

    private Path writeImageToFile(MultipartFile image, String subPath) throws IOException {
        String fileName = UUID.randomUUID() + getExtension(image.getOriginalFilename());
        Path filePath = Path.of(imageDirectory, subPath, fileName);
        Files.createDirectories(filePath.getParent());
        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
        }
        return filePath;
    }

    @Override
    public void deleteImage(String filename, String subPath) throws IOException {
        Path filePath = Path.of(imageDirectory, subPath, filename);
        Files.deleteIfExists(filePath);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    private String stripUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
