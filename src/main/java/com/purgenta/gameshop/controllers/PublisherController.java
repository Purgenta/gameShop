package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.models.game.Publisher;
import com.purgenta.gameshop.services.publisher.IPublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publishers")
public class PublisherController {
    private final IPublisherService publisherService;
    @PostMapping("/addPublisher")
    public ResponseEntity<?> addPublisher(@RequestBody @Valid Publisher publisher) {
        return publisherService.addPublisher(publisher);
    }
    @PutMapping("/updatePublisher/{publisher_id}")
    public ResponseEntity<?> updatePublisher(@RequestBody @Valid Publisher publisher, @PathVariable int publisher_id) {
        return publisherService.updatePublisher(publisher,publisher_id);
    }
    @DeleteMapping("/deletePublisher/{publisher_id}")
    public ResponseEntity<?> deletePublisher(@PathVariable int publisher_id) {
        return publisherService.deletePublisher(publisher_id);
    }
    @GetMapping("/getPublishers")
    public List<Publisher> getPublishers() {
        return publisherService.getPublishers();
    }
}
