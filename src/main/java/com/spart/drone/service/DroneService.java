package com.spart.drone.service;

import com.spart.drone.controller.dto.drone.DroneUpdateBatteryCapacityDto;
import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.controller.dto.drone.DroneUpdateStateDto;
import com.spart.drone.service.status.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DroneService {
    Long addDrone(DroneDto droneDto);

    Response loadMedication(Long droneId, Long medicationId) ;

    List<DroneDto> getAllDrones(Boolean availableOnly);

    List<MedicationDto> getDroneMedication(Long droneId);

    List<DroneDto> getDronesWithoutMedication();

    Integer getDronesBatteryLevel(Long droneId);

    void getBatteryLevelStatistics();

    DroneDto getDroneById(Long droneId);

    Long setBatteryLevel(DroneUpdateBatteryCapacityDto droneDto);

    Long deleteDrone(Long droneId);

    Long deleteMedicationFromDrone(Long droneId);

    Long setState(DroneUpdateStateDto droneUpdateStateDto);
}
