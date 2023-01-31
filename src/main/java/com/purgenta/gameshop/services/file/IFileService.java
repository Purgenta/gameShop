package com.purgenta.gameshop.services.file;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    String saveProductImage (MultipartFile imageFile) throws IOException;
    ResponseEntity<?> getProductImage(String photoName);
}
