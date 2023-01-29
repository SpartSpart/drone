package com.spart.drone.service;

import com.spart.drone.controller.dto.MedicationDto;
import org.springframework.stereotype.Service;

@Service
public interface MedicationService {
    Long add(MedicationDto medicationDto);
}
