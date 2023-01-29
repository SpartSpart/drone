package com.spart.drone.controller;

import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.exception.DroneCountLimitException;
import com.spart.drone.exception.NoSuchElementInDatabase;
import com.spart.drone.service.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Drone controller allows manage drones
 */
@RestController
@RequestMapping("/api/v1/drone")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    /**
     * Method for registration drone in service
     * @param droneDto
     * @return
     */
    @PostMapping
    public ResponseEntity registerDrone(@RequestBody @Valid DroneDto droneDto){
        try {
            return ResponseEntity.ok().body(droneService.registerDrone(droneDto));
        }
        catch (NoSuchElementInDatabase|DroneCountLimitException|Exception exeption){
            return ResponseEntity.ok().body(exeption.getMessage());
        }
    }

    @PostMapping("/load")
    public ResponseEntity loadMedicationToDrone(@RequestParam(name = "droneId") Long droneId,
                                                @RequestParam(name = "medicationId") Long medicationId){
        return ResponseEntity.ok().build();
    }
}
