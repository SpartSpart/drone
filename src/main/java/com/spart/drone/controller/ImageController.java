package com.spart.drone.controller;

import com.spart.drone.service.ImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Model controller allows working with drone models
 */
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @PostMapping()
    public Long addMedicationWithImage(@RequestBody MultipartFile image) {
        try {
            return imageService.addWithImage(image);
        } catch (Exception e) {
            //TODO
            return null;
        }
    }
}
