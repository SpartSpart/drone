package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.MedicationRepository;
import com.spart.drone.repository.model.MedicationEntity;
import com.spart.drone.service.MedicationService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final ModelMapper modelMapper;


    public MedicationServiceImpl(MedicationRepository medicationRepository, ModelMapper modelMapper) {
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long add(MedicationDto medicationDto) {
        MedicationEntity medicationEntity = modelMapper.map(medicationDto, MedicationEntity.class);
        return medicationRepository.save(medicationEntity).getId();
    }

    @Override
    public MedicationEntity getMedicationById(Long medicationId)  {
        return Optional.ofNullable(medicationRepository.findById(medicationId)).get().orElseThrow(()->new NoSuchElementInDatabaseException(MedicationEntity.class.getName()));
    }
}
