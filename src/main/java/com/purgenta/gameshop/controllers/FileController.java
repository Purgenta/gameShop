package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.services.file.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final IFileService fileService;
    @GetMapping("/products/{photoName}")
    public ResponseEntity<?> getProductImage(@PathVariable("photoName") String photoName) {
        return fileService.getProductImage(photoName);
    }
}
