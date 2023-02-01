package com.spart.drone.controller;

import com.spart.drone.controller.dto.DroneUpdateBatteryCapacityDto;
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
        return droneService.addDrone(droneDto);
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

    @GetMapping("medication/{droneId}")
    public List<MedicationDto> getDroneMedication(@PathVariable Long droneId){
        return droneService.getDroneMedication(droneId);
    }

    @GetMapping("/empty")
    public List<DroneDto> getDronesWithoutMedication(){
        return droneService.getDronesWithoutMedication();
    }

    @GetMapping("/battery/{droneId}")
    public Integer getDroneBatteryLevel(@PathVariable Long droneId){
        return droneService.getDronesBatteryLevel(droneId);
    }

    @GetMapping("{droneId}")
    public DroneDto getDroneById(@PathVariable Long droneId){
        return droneService.getDroneById(droneId);
    }

    @PutMapping()
    public Long setBatteryLevel(@RequestBody DroneUpdateBatteryCapacityDto droneUpdateBatteryCapacityDto){
        return droneService.setBatteryLevel(droneUpdateBatteryCapacityDto);
    }
 }
