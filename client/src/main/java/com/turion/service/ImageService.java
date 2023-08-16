package com.turion.service;

import com.turion.resources.repo.ImageRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ImageService {

    private final ImageRepo imageRepo;

    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public byte[] getAllImagesByRequestId(Integer requestId) {
        return imageRepo
                .findByRequestId(requestId)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "image not found"))
                .getData();
    }
}
