package com.spart.drone.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {
    Long addWithImage(MultipartFile image) throws IOException;
}