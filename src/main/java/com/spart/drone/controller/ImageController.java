package com.spart.drone.controller;

import com.spart.drone.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class ImageController {

    private static final String IMAGE_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/image";

    private final ImageService imageService;

    @PostMapping(IMAGE_BASE_PATH)
    public Long addMedicationImage(@RequestBody MultipartFile image) {
            return imageService.addImage(image);
    }
}
