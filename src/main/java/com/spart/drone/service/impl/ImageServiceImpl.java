package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.ImageDto;
import com.spart.drone.exception.ImageException;
import com.spart.drone.repository.ImageRepository;
import com.spart.drone.repository.mapper.ImageMapper;
import com.spart.drone.repository.model.ImageEntity;
import com.spart.drone.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    @Transactional
    public Long addWithImage(MultipartFile image){
        try {
            ImageDto imageDto = new ImageDto();
            byte[] imageByteArray = convertImageToByteArray(image);

            System.out.println(imageByteArray);
            imageDto.setImage(imageByteArray);
            ImageEntity imageEntity = imageMapper.toModel(imageDto);
            imageEntity = imageRepository.save(imageEntity);
            log.info(String.format("Image added"));
            return imageEntity.getId();
        }
        catch (Exception e){
            throw new ImageException();
        }
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
