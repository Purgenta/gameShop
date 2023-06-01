package com.purgenta.gameshop.controllers;



import com.purgenta.gameshop.services.game.IGameCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final IGameCategoryService iGameCategoryService;
    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getCategories() {
        return new ResponseEntity<>(iGameCategoryService.getCategories(), HttpStatus.OK);
    }
}
