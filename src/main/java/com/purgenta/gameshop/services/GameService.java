package com.purgenta.gameshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {
    private final FileService fileService;
    @Override
    public ResponseEntity<Map<String, String>> addGame(MultipartFile file) {
        Map<String,String> response = new HashMap<>();
        try {
           response.put("image_path",fileService.saveProductImage(file));
        } catch (IOException e) {
            System.out.println(e);
            response.clear();
            response.put("error","Issues with processing your image file");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
