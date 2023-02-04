package com.spart.drone.controller;

import com.spart.drone.controller.dto.drone.DroneUpdateBatteryCapacityDto;
import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.controller.dto.drone.DroneUpdateStateDto;
import com.spart.drone.service.DroneService;
import com.spart.drone.service.status.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class DroneController {

    private static final String DRONE_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/drone";
    private static final String DRONE_LOAD_MEDICATION = DRONE_BASE_PATH + "/load";
    private static final String DRONE_MEDICATION = DRONE_BASE_PATH + "/medication";
    private static final String DRONE_STATE = DRONE_BASE_PATH + "/state";
    private static final String DRONE_BATTERY = DRONE_BASE_PATH + "/battery";
    private static final String GET_EMPTY_DRONES = DRONE_BASE_PATH + "/empty";

    private final DroneService droneService;

    @PostMapping(DRONE_BASE_PATH)
    public Long addDrone(@RequestBody @Valid DroneDto droneDto){
        return droneService.addDrone(droneDto);
    }

    @PostMapping(DRONE_LOAD_MEDICATION)
    public Response loadMedicationToDrone(@RequestParam(name = "droneId") Long droneId,
                                          @RequestParam(name = "medicationId") Long medicationId){
            return droneService.loadMedication(droneId, medicationId);
    }

    @GetMapping(DRONE_BASE_PATH)
    public List<DroneDto> getAllDrones(@RequestParam(name = "availableOnly", required = false, defaultValue = "false") Boolean availableOnly ){
        return droneService.getAllDrones(availableOnly);
    }

    @GetMapping(DRONE_MEDICATION + "/{droneId}")
    public List<MedicationDto> getDroneMedication(@PathVariable Long droneId){
        return droneService.getDroneMedication(droneId);
    }

    @GetMapping(GET_EMPTY_DRONES)
    public List<DroneDto> getDronesWithoutMedication(){
        return droneService.getDronesWithoutMedication();
    }

    @GetMapping(DRONE_BASE_PATH + "/{droneId}")
    public DroneDto getDroneById(@PathVariable Long droneId){
        return droneService.getDroneById(droneId);
    }

    @PutMapping(DRONE_BATTERY)
    public Long setBatteryLevel(@RequestBody @Valid DroneUpdateBatteryCapacityDto droneUpdateBatteryCapacityDto){
        return droneService.setBatteryLevel(droneUpdateBatteryCapacityDto);
    }

    @PutMapping(DRONE_STATE)
    public Long setState(@RequestBody @Valid DroneUpdateStateDto droneUpdateStateDto){
        return droneService.setState(droneUpdateStateDto);
    }

    @DeleteMapping(DRONE_BASE_PATH + "/{droneId}")
    public Long deleteDrone(@PathVariable Long droneId){
        return droneService.deleteDrone(droneId);
    }

    @DeleteMapping(DRONE_MEDICATION + "/{droneId}")
    public Long deleteMedicationFromDrone(@PathVariable Long droneId){
        return droneService.deleteMedicationFromDrone(droneId);
    }
 }
