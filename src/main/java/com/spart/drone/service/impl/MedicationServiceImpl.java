package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.MedicationRepository;
import com.spart.drone.repository.mapper.MedicationMapper;
import com.spart.drone.repository.model.MedicationEntity;
import com.spart.drone.service.MedicationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;


    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    @Override
    @Transactional
    public Long add(MedicationDto medicationDto) {
        MedicationEntity medicationEntity = medicationMapper.toModel(medicationDto);
        return medicationRepository.save(medicationEntity).getId();
    }

    @Override
    @Transactional
    public MedicationEntity getMedicationById(Long medicationId)  {
        return Optional.ofNullable(medicationRepository.findById(medicationId)).get().orElseThrow(()->new NoSuchElementInDatabaseException(MedicationEntity.class.getName()));
    }

    @Override
    @Transactional
    public List<MedicationDto> getAllMedication() {
        List<MedicationDto> medicationDtoList = medicationMapper.toListDto(medicationRepository.findAll());
        return medicationDtoList;
    }
}
