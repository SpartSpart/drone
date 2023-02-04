package com.spart.drone.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    Long addImage(MultipartFile image);
}