package com.purgenta.gameshop.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IGameService {
    ResponseEntity<Map<String, String>> addGame(MultipartFile file);
}
