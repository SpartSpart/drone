package com.spart.drone.service;

import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.exception.DroneCountLimitException;
import com.spart.drone.exception.NoSuchElementInDatabase;
import org.springframework.stereotype.Service;

@Service
public interface DroneService {
    Long registerDrone(DroneDto droneDto) throws NoSuchElementInDatabase, DroneCountLimitException;
}
