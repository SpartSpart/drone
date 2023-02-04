package com.spart.drone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.controller.dto.drone.DroneUpdateBatteryCapacityDto;
import com.spart.drone.controller.dto.drone.DroneUpdateStateDto;
import com.spart.drone.service.DroneService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(DroneController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DroneControllerTest {

    private static final String DRONE_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/drone";
    private static final String DRONE_LOAD_MEDICATION = DRONE_BASE_PATH + "/load";
    private static final String DRONE_MEDICATION = DRONE_BASE_PATH + "/medication";
    private static final String DRONE_STATE = DRONE_BASE_PATH + "/state";
    private static final String DRONE_BATTERY = DRONE_BASE_PATH + "/battery";
    private static final String GET_EMPTY_DRONES = DRONE_BASE_PATH + "/empty";

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    ObjectMapper objectMapper;

    @MockBean
    private DroneService droneService;

    private DroneDto droneDtoValid;
    private DroneDto droneDtoWrongPatternSerialNumber;
    private DroneDto droneDtoBatteryCapacityMoreThen100;
    private DroneUpdateBatteryCapacityDto droneUpdateBatteryCapacityDto;
    private DroneUpdateStateDto droneUpdateStateDto;

    @BeforeEach
    public void init() {
        String serialNumberMoreThen100Symbols = "SN_1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        droneDtoValid = new DroneDto(1L, "DRONE", "SN_123", new ModelDto(), 100, new StateDto(), null);
        droneDtoBatteryCapacityMoreThen100 = new DroneDto(1L, "DRONE", "SN_123", new ModelDto(), 101, new StateDto(), null);
        droneDtoWrongPatternSerialNumber = new DroneDto(1L, "DRONE", serialNumberMoreThen100Symbols, new ModelDto(), 100, new StateDto(), null);
        droneUpdateBatteryCapacityDto = new DroneUpdateBatteryCapacityDto(1L,100);
        droneUpdateStateDto = new DroneUpdateStateDto(1L,new StateDto());
    }

    @Test
    public void addDrone_sendValidDroneDto_returnOk() throws Exception {
        mockMvc.perform(post(DRONE_BASE_PATH)
                        .content(objectMapper.writeValueAsString(droneDtoValid))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addDrone_droneDtoBatteryCapacityMoreThen100_returnInternalServerError() throws Exception {
        mockMvc.perform(post(DRONE_BASE_PATH)
                        .content(objectMapper.writeValueAsString(droneDtoBatteryCapacityMoreThen100))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void addDrone_droneDtoWrongPatternSerialNumber_returnInternalServerError() throws Exception {
        mockMvc.perform(post(DRONE_BASE_PATH)
                        .content(objectMapper.writeValueAsString(droneDtoWrongPatternSerialNumber))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    public void loadMedicationToDrone_returnOk() throws Exception {
        mockMvc.perform(post(DRONE_LOAD_MEDICATION + "?droneId=1&medicationId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAllDrones_returnOk() throws Exception {
        mockMvc.perform(get(DRONE_BASE_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDroneMedication_returnOk() throws Exception {
        mockMvc.perform(get(DRONE_MEDICATION + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDronesWithoutMedication_returnOk() throws Exception {
        mockMvc.perform(get(GET_EMPTY_DRONES))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDroneById_returnOk() throws Exception {
        given(droneService.getDroneById(1L))
                .willReturn(droneDtoValid);
        assert mockMvc.perform(get(DRONE_BASE_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(droneDtoValid.getName());
    }

    @Test
    public void setBatteryLevel_returnOk() throws Exception {
        mockMvc.perform(put(DRONE_BATTERY)
                        .content(objectMapper.writeValueAsString(droneUpdateBatteryCapacityDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void setState_returnOk() throws Exception {
        mockMvc.perform(put(DRONE_STATE)
                        .content(objectMapper.writeValueAsString(droneUpdateStateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteDrone_returnOk() throws Exception {
        mockMvc.perform(delete(DRONE_BASE_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteMedicationFromDrone_returnOk() throws Exception {
        mockMvc.perform(delete(DRONE_MEDICATION + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}