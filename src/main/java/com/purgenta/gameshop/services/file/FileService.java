package com.purgenta.gameshop.services.file;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileService implements IFileService {
    private final String photosPath = "/photos";
    private final String productsPath = "/products";
    @Override
    public String saveProductImage(MultipartFile imageFile) throws IOException {
        byte[] bytes = imageFile.getBytes();
        String fileName = imageFile.getOriginalFilename();
        String filePath = String.format("%s/%s/%s",photosPath,productsPath,fileName);
        Path path = Paths.get(filePath);
        Files.write(path,bytes);
        return filePath;
    }

    @Override
    public ResponseEntity<?> getProductImage(String photoName) {
        String filePath = String.format("%s/%s/%s",photosPath,productsPath,photoName);
        Path path = Paths.get(filePath);
        String extension = "";
        int i = photoName.lastIndexOf('.');
        if (i > 0) {
            extension = photoName.substring(i+1);
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
            Map<String,String> response= new HashMap<>();
            response.put("errorMessage","no such image exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
