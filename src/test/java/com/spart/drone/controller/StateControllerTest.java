package com.spart.drone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.service.StateService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(StateController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StateControllerTest {

    private static final String STATE_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/state";

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    StateService stateService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    ObjectMapper objectMapper;

    private StateDto validStateDto;

    private StateDto invalidStateDto;

    @BeforeEach
    public void init() {
        validStateDto = new StateDto(1L, "State");
        invalidStateDto = new StateDto(1L, "");
    }

    @Test
    public void addState_sendValidDto_returnOk() throws Exception {
        mockMvc.perform(post(STATE_BASE_PATH)
                        .content(objectMapper.writeValueAsString(validStateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addState_sendInvalidDto_returnInternalServerError() throws Exception {
        mockMvc.perform(post(STATE_BASE_PATH)
                        .content(objectMapper.writeValueAsString(invalidStateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void getAllStates_ReturnOk() throws Exception {
        mockMvc.perform(get(STATE_BASE_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}