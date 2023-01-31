package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.ImageDto;
import com.spart.drone.repository.ImageRepository;
import com.spart.drone.repository.mapper.ImageMapper;
import com.spart.drone.repository.model.ImageEntity;
import com.spart.drone.service.ImageService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    @Transactional
    public Long addWithImage(MultipartFile image) throws IOException {
        ImageDto imageDto = new ImageDto();
        byte[] imageByteArray = convertImageToByteArray(image);

        System.out.println(imageByteArray);
        imageDto.setImage(imageByteArray);
        ImageEntity imageEntity = imageMapper.toModel(imageDto);
        return imageRepository.save(imageEntity).getId();
    }

    private byte[] convertImageToByteArray(MultipartFile image) throws IOException {
        byte[] byteArray = null;
        byteArray = new byte[image.getBytes().length];
        int i = 0;
        for (byte b : image.getBytes()) {
            byteArray[i++] = b;
        }

        return byteArray;
    }
}
