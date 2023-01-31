package com.spart.drone.service;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.DroneDto;
import com.spart.drone.service.status.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DroneService {
    Long registerDrone(DroneDto droneDto);

    Response loadMedication(Long droneId, Long medicationId) ;

    List<DroneDto> getAllDrones(Boolean availableOnly);

    List<MedicationDto> getDroneMedication(Long droneId);

    List<DroneDto> getDronesWithoutMedication();
}
