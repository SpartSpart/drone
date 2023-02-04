package com.spart.drone.controller;

import com.spart.drone.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {

    private static final String IMAGE_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/image";

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    ImageService imageService;

    private MockMultipartFile image;

    @BeforeEach
    public void init() {
        image = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.TEXT_PLAIN_VALUE,
                "image".getBytes());
    }

    @Test
    public void addState_sendValidDto_returnOk() throws Exception {
        mockMvc.perform(multipart(IMAGE_BASE_PATH).file(image))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}