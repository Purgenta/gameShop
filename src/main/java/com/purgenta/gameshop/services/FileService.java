package com.purgenta.gameshop.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class FileService implements IFileService{
    private final String photosPath = "/photos";
    @Override
    public String saveProductImage(MultipartFile imageFile) throws IOException {
        byte[] bytes = imageFile.getBytes();
        String fileName = imageFile.getOriginalFilename();
        String filePath = String.format("%s/products/%s",photosPath,fileName);
        Path path = Paths.get(filePath);
        Files.write(path,bytes);
        return filePath;
    }
}
