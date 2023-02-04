package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.controller.dto.drone.DroneUpdateBatteryCapacityDto;
import com.spart.drone.controller.dto.drone.DroneUpdateStateDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.DroneRepository;
import com.spart.drone.repository.mapper.DroneMapper;
import com.spart.drone.repository.mapper.StateMapper;
import com.spart.drone.repository.model.MedicationEntity;
import com.spart.drone.service.DroneService;
import com.spart.drone.service.MedicationService;
import com.spart.drone.service.ModelService;
import com.spart.drone.service.StateService;
import com.spart.drone.service.status.Code;
import com.spart.drone.service.status.Response;
import com.spart.drone.service.validator.DroneValidator;
import com.spart.drone.service.validator.ValidationMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(value = "classpath:test_data_h2.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:clean_data_h2.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DroneServiceImplTest {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private ModelService modelService;

    @Autowired
    private StateService stateService;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private DroneMapper droneMapper;

    @Autowired
    private DroneValidator droneValidator;

    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private DroneService droneService;

    @Test
    public void loadMedication_whenSendLowBatteryLevelDroneForLoading_returnErrorLowBatteryLevel() {
        Response response = droneService.loadMedication(1L, 1L);
        assertEquals(response.getStatus().getCode(), Code.error);
        assertEquals(response.getStatus().getErrors().size(), 1);
        assertEquals(response.getStatus().getErrors().get(0), ValidationMessages.LOW_BATTERY_LEVEL + droneValidator.getBATTERY_CAPACITY_LIMIT());
    }

    @Test
    public void loadMedication_whenSendLowBatteryLevelDroneAndStateLOADING_returnErrors() {
        Response response = droneService.loadMedication(2L, 1L);
        assertEquals(response.getStatus().getCode(), Code.error);
        assertEquals(response.getStatus().getErrors().size(), 2);
        assertEquals(response.getStatus().getErrors().get(0), ValidationMessages.LOW_BATTERY_LEVEL + droneValidator.getBATTERY_CAPACITY_LIMIT());
        assertEquals(response.getStatus().getErrors().get(1), ValidationMessages.DRONE_IS_IN_LOADING_STATE.toString());
    }

    @Test
    public void loadMedication_droneIsNotValidForLoadingByWeight_returnWeightError() {
        droneService.deleteMedicationFromDrone(4L);
        DroneDto droneDto = droneService.getDroneById(4L);
        MedicationEntity medicationEntity = medicationService.getMedicationById(5L);

        int MAX_DRONE_WEIGHT = droneDto.getModel().getWeightLimit();
        int MEDICATION_WEIGHT = medicationEntity.getWeight();

        for (int i = 0; i < MAX_DRONE_WEIGHT / MEDICATION_WEIGHT; i++)
            droneService.loadMedication(4L, 5L);

        Response response = droneService.loadMedication(4L, 5L);

        assertEquals(response.getStatus().getCode(), Code.error);
        assertEquals(response.getStatus().getErrors().size(), 1);
        assertEquals(response.getStatus().getErrors().get(0), ValidationMessages.LIMIT_LOAD_WEIGHT.toString());
    }

    @Test
    public void loadMedication_droneIsValidForLoading_returnStatusCodeOk() {
        Response response = droneService.loadMedication(4L, 1L);
        assertEquals(response.getStatus().getCode(), Code.ok);
        assertEquals(response.getStatus().getErrors(), null);
    }

    @Test
    public void getAllDrones_whenNeedToGetAvailableAndUnAvailableForLoadingDrones_returnAllDrones() {
        List<DroneDto> droneDtoList = droneService.getAllDrones(false);
        assertEquals(droneDtoList.size(), 5);
    }

    @Test
    public void getAllDrones_whenNeedToGetOnlyAvailableForLoadingDrones_returnAvailableForLoadingDrones() {
        List<DroneDto> droneDtoList = droneService.getAllDrones(true);
        assertEquals(droneDtoList.size(), 2);
    }

    @Test
    public void getDroneMedication_whenDroneExistsInDB_returnDroneMedicationList() {
        List<MedicationDto> medicationDtoList = droneService.getDroneMedication(3L);
        assertEquals(medicationDtoList.size(), 3);
        assertEquals(medicationDtoList.get(0).getName(), "Aspirin_01-1");
        assertEquals(medicationDtoList.get(0).getCode(), "AS_01");
    }

    @Test
    public void getDroneMedication_whenDroneNotExistsInDB_returnNoSuchElementInDatabaseException() {
        Throwable thrown = catchThrowable(() ->
                droneService.getDroneMedication(10L));

        assertThat(thrown).isInstanceOf(NoSuchElementInDatabaseException.class);
    }

    @Test
    public void getDronesWithoutMedication_returnAllDronesWithoutMedications() {
        List<DroneDto> dronesWithoutMedication = droneService.getDronesWithoutMedication();
        assertEquals(dronesWithoutMedication.size(), 2);
    }

    @Test
    public void getDronesBatteryLevel_whenDroneExistsInDB_returnDroneBatteryLevel() {
        Integer dronesBatteryLevel = droneService.getDronesBatteryLevel(1L);
        assertEquals(dronesBatteryLevel, 20);
    }

    @Test
    public void getDroneById_whenDroneExistsInDB_returnDroneto() {
        DroneDto droneDto = droneService.getDroneById(1L);
        assertEquals(droneDto.getName(), "DRONE_1");
        assertEquals(droneDto.getSerialNumber(), "SN345728346582365");
        assertEquals(droneDto.getModel().getName(), "Lightweight");
        assertEquals(droneDto.getState().getName(), "IDLE");
        assertEquals(droneDto.getMedicationDto().size(), 0);
    }

    @Test
    public void setBatteryLevel_whenDroneExists_thenBatteryLevelWasChanged() {
        DroneUpdateBatteryCapacityDto droneUpdateBatteryCapacityDto = new DroneUpdateBatteryCapacityDto(1L, 55);

        Integer droneCurrentBatteryLevel = droneService.getDronesBatteryLevel(1L);
        droneService.setBatteryLevel(droneUpdateBatteryCapacityDto);
        Integer droneUpdatedBatteryLevel = droneService.getDronesBatteryLevel(1L);

        assertNotEquals(droneCurrentBatteryLevel, droneUpdatedBatteryLevel);
    }

    @Test
    public void deleteDrone_whenDroneExists_thenDroneDeleted() {
        List<DroneDto> droneDtoListBeforeDeleting = droneService.getAllDrones(false);
        droneService.deleteDrone(1L);

        List<DroneDto> droneDtoListAfterDeleting = droneService.getAllDrones(false);

        assertEquals(droneDtoListAfterDeleting.size(), 4);
        assertThat(droneDtoListBeforeDeleting.stream().anyMatch(droneDto -> droneDto.getId() == 1L)).isTrue();
        assertThat(droneDtoListAfterDeleting.stream().anyMatch(droneDto -> droneDto.getId() == 1L)).isFalse();
    }

    @Test
    public void deleteMedicationFromDrone_whenDroneExists_thenDeleteAllMedications() {
        List<MedicationDto> medicationBeforeDeletingDtoList = droneService.getDroneMedication(3L);
        droneService.deleteMedicationFromDrone(3L);
        List<MedicationDto> medicationAfterDeletingDtoList = droneService.getDroneMedication(3L);

        assertNotEquals(medicationBeforeDeletingDtoList.size(), medicationAfterDeletingDtoList.size());
        assertEquals(medicationAfterDeletingDtoList.size(), 0);
    }

    @Test
    public void setState_whenDroneExists_thenStateWasChanged() {
        DroneUpdateStateDto droneUpdateStateDto = new DroneUpdateStateDto(1L, new StateDto(2L, "LOADING"));

        StateDto stateDtoBeforeChanging = droneService.getDroneById(1L).getState();
        droneService.setState(droneUpdateStateDto);

        StateDto stateDtoAfterChanging = droneService.getDroneById(1L).getState();

        assertNotEquals(stateDtoBeforeChanging, stateDtoAfterChanging);
        assertEquals(stateDtoBeforeChanging.getName(), "IDLE");
        assertEquals(stateDtoAfterChanging.getName(), "LOADING");
    }
}