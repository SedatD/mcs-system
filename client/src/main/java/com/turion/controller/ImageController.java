package com.turion.controller;

import com.turion.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msc/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{requestId}")
    public ResponseEntity<byte[]> getAllImagesByRequestId(@PathVariable Integer requestId) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageService.getAllImagesByRequestId(requestId));
    }
}
