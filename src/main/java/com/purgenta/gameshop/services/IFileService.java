package com.purgenta.gameshop.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {
    String saveProductImage (MultipartFile imageFile) throws IOException;
}
