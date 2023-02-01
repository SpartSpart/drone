package com.spart.drone.controller;

import com.spart.drone.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @PostMapping()
    public Long addMedicationWithImage(@RequestBody MultipartFile image) {
            return imageService.addWithImage(image);
    }
}
