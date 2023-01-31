package com.spart.drone.service;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.repository.model.MedicationEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicationService {
    Long add(MedicationDto medicationDto);

    MedicationEntity getMedicationById(Long medicationId);

    List<MedicationDto> getAllMedication();
}
