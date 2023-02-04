package com.spart.drone.service.impl;

import com.spart.drone.repository.ImageRepository;
import com.spart.drone.repository.mapper.ImageMapper;
import com.spart.drone.repository.model.ImageEntity;
import com.spart.drone.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ImageServiceImplTest {

    @MockBean
    ImageRepository imageRepository;

    @MockBean
    ImageMapper imageMapper;

    @Autowired
    ImageService imageService;

    private MockMultipartFile image;

    private ImageEntity imageEntity;


    @BeforeEach
    public void init() {
        image = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.TEXT_PLAIN_VALUE,
                "image".getBytes());

        imageEntity = new ImageEntity();
        imageEntity.setId(1L);
    }

    @Test
    public void addImage_sendImageFile_returnSavedImageId() {
        when(imageRepository.save(any())).thenReturn(imageEntity);

        assertEquals(imageService.addImage(image), 1L);
    }
}