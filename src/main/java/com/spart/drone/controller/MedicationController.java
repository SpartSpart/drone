package com.spart.drone.controller;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.repository.mapper.MedicationMapper;
import com.spart.drone.service.MedicationService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Drone controller allows manage medications
 */
@RestController
@RequestMapping("/api/v1/medication")
public class MedicationController {

    private final MedicationService medicationService;
    private final MedicationMapper medicationMapper;

    public MedicationController(MedicationService medicationService, MedicationMapper medicationMapper) {
        this.medicationService = medicationService;
        this.medicationMapper = medicationMapper;
    }

    /**
     * Method for add medication to service
     *
     * @param medicationDto
     * @return
     */
    @PostMapping
    public Long addMedication(@RequestBody @Valid MedicationDto medicationDto) {
        return medicationService.add(medicationDto);
    }

    @GetMapping
    public List<MedicationDto> getAllMedication() {
        return medicationService.getAllMedication();
    }

    @GetMapping("{medicationId}")
    public MedicationDto getMedicationById(@PathVariable Long medicationId) {
        return medicationMapper.toDto(medicationService.getMedicationById(medicationId));
    }
}
