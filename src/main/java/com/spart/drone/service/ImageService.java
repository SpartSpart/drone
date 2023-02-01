package com.spart.drone.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    Long addWithImage(MultipartFile image);
}