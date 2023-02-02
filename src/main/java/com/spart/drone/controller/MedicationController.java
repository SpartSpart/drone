package com.spart.drone.controller;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.repository.mapper.MedicationMapper;
import com.spart.drone.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class MedicationController {

    private static final String MEDICATION_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/medication";

    private final MedicationService medicationService;
    private final MedicationMapper medicationMapper;

    @PostMapping(MEDICATION_BASE_PATH)
    public Long addMedication(@RequestBody @Valid MedicationDto medicationDto) {
        return medicationService.add(medicationDto);
    }

    @GetMapping(MEDICATION_BASE_PATH)
    public List<MedicationDto> getAllMedication() {
        return medicationService.getAllMedication();
    }

    @GetMapping(MEDICATION_BASE_PATH + "/{medicationId}")
    public MedicationDto getMedicationById(@PathVariable Long medicationId) {
        return medicationMapper.toDto(medicationService.getMedicationById(medicationId));
    }
}
