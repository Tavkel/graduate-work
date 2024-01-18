package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.EntityWithImage;

import java.io.IOException;

public interface ImageService {
    String saveImage (MultipartFile image, EntityWithImage entity) throws IOException;

    void deleteImage(String filename, String subPath) throws IOException;
}
