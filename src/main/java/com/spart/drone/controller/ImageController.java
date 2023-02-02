package com.spart.drone.controller;

import com.spart.drone.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping()
public class ImageController {

    private static final String IMAGE_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/image";

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @PostMapping(IMAGE_BASE_PATH)
    public Long addMedicationWithImage(@RequestBody MultipartFile image) {
            return imageService.addWithImage(image);
    }
}
