package com.spart.drone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ModelController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ModelControllerTest {

    private static final String MODEL_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/model";

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    ModelService modelService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    ObjectMapper objectMapper;

    private ModelDto validModelDto;

    private ModelDto invalidModelDto;

    @BeforeEach
    public void init() {
        validModelDto = new ModelDto(1L, "Model", 500);
        invalidModelDto = new ModelDto(1L, "Model", 700);
    }

    @Test
    public void saveModel_sendValidDto_returnOk() throws Exception {
        mockMvc.perform(post(MODEL_BASE_PATH)
                        .content(objectMapper.writeValueAsString(validModelDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void saveModel_sendInvalidDto_returnInternalServerError() throws Exception {
        mockMvc.perform(post(MODEL_BASE_PATH)
                        .content(objectMapper.writeValueAsString(invalidModelDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void getAllModels_ReturnOk() throws Exception {
        given(modelService.getAllModel())
                .willReturn(new ArrayList<>());

        mockMvc.perform(get(MODEL_BASE_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}