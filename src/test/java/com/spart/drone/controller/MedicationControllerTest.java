package com.spart.drone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.repository.mapper.MedicationMapper;
import com.spart.drone.repository.model.MedicationEntity;
import com.spart.drone.service.MedicationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(MedicationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MedicationControllerTest {
    private static final String MEDICATION_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/medication";

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    ObjectMapper objectMapper;

    @MockBean
    MedicationService medicationService;

    @MockBean
    MedicationMapper medicationMapper;

    private MedicationDto medicationDtoWithWrongPatternName;

    private MedicationDto medicationDtoWithZeroWeight;

    private MedicationDto medicationDtoWithWrongPatternCode;

    private MedicationDto medicationDtoValid;


    @BeforeEach
    public void init() {
        medicationDtoValid = new MedicationDto(1L, "Medication-1_Z", 100, "A_0", null);
        medicationDtoWithWrongPatternName = new MedicationDto(1L, "Medication-1_Z*", 100, "A_0", null);
        medicationDtoWithWrongPatternCode = new MedicationDto(1L, "Medication-1_Z", 100, "A0_", null);
        medicationDtoWithZeroWeight = new MedicationDto(1L, "Medication-1_Z", 0, "A_0", null);
    }

    @Test
    public void addMedication_sendValidMedicationDto_ReturnOk() throws Exception {
        mockMvc.perform(post(MEDICATION_BASE_PATH)
                        .content(objectMapper.writeValueAsString(medicationDtoValid))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addMedication_medicationDtoWithWrongPatternCode_ReturnInternalServerError() throws Exception {
        mockMvc.perform(post(MEDICATION_BASE_PATH)
                        .content(objectMapper.writeValueAsString(medicationDtoWithWrongPatternCode))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void addMedication_medicationDtoWithWrongPatternName_ReturnInternalServerError() throws Exception {
        mockMvc.perform(post(MEDICATION_BASE_PATH)
                        .content(objectMapper.writeValueAsString(medicationDtoWithWrongPatternName))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void addMedication_medicationDtoWithZeroWeight_ReturnInternalServerError() throws Exception {
        mockMvc.perform(post(MEDICATION_BASE_PATH)
                        .content(objectMapper.writeValueAsString(medicationDtoWithZeroWeight))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void getAllMedication_ReturnOk() throws Exception {
        mockMvc.perform(get(MEDICATION_BASE_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ggetMedicationById_ReturnOk() throws Exception {
        given(medicationService.getMedicationById(any()))
                .willReturn(new MedicationEntity());

        mockMvc.perform(get(MEDICATION_BASE_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}