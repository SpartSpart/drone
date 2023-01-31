package com.spart.drone.controller;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.DroneDto;
import com.spart.drone.service.DroneService;
import com.spart.drone.service.status.Response;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public Long addDrone(@RequestBody @Valid DroneDto droneDto){
        return droneService.registerDrone(droneDto);
    }

    @PostMapping("/load")
    public Response loadMedicationToDrone(@RequestParam(name = "droneId") Long droneId,
                                          @RequestParam(name = "medicationId") Long medicationId){
            return droneService.loadMedication(droneId, medicationId);
    }


    @GetMapping
    public List<DroneDto> getAllDrones(@RequestParam(name = "availableOnly", required = false, defaultValue = "false") Boolean availableOnly ){
        return droneService.getAllDrones(availableOnly);
    }

    @GetMapping("{droneId}")
    public List<MedicationDto> getDroneMedication(@PathVariable Long droneId){
        return droneService.getDroneMedication(droneId);
    }

    @GetMapping("/empty")
    public List<DroneDto> getDronesWithoutMedication(){
        return droneService.getDronesWithoutMedication();
    }
 }
