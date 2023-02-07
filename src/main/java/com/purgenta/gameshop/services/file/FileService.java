package com.purgenta.gameshop.services.file;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class FileService implements IFileService {
    private final String photosPath = "photos";
    private final String productsPath = "products";
    @Override
    public String saveProductImage(MultipartFile imageFile) throws IOException {
        byte[] bytes = imageFile.getBytes();
        String fileName = imageFile.getOriginalFilename();
        long now = Instant.now().getEpochSecond();
        String constructedName = String.format("%s%s",now,fileName);
        String filePath = String.format("/%s/%s/%s",photosPath,productsPath,constructedName);
        Path path = Paths.get(filePath);
        Files.write(path,bytes);
        return String.format("/%s%s","files",filePath);
    }

    @Override
    public ResponseEntity<?> getProductImage(String photoPath) {
        Path path = Paths.get(photoPath);
        String extension = "";
        int i = photoPath.lastIndexOf('.');
        if (i > 0) {
            extension = photoPath.substring(i+1);
        }
        try {
            MediaType contentType;
            if(extension.equals("jpg")) {
                contentType = MediaType.IMAGE_JPEG;
            } else {
                contentType = MediaType.IMAGE_PNG;
            }
            byte[] bytes = Files.readAllBytes(path);
            return ResponseEntity.ok().contentType(contentType).body(bytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
    public void removeProductImage(String productImage) throws IOException {
        Path path = Paths.get(productImage);
        Files.delete(path);
    }
}
