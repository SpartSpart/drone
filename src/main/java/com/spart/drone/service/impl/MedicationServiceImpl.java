package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.MedicationRepository;
import com.spart.drone.repository.mapper.MedicationMapper;
import com.spart.drone.repository.model.MedicationEntity;
import com.spart.drone.service.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;


    @Override
    @Transactional
    public Long add(MedicationDto medicationDto) {
        MedicationEntity medicationEntity = medicationRepository.save(medicationMapper.toModel(medicationDto));
        log.info(String.format("Medication %s added",medicationEntity.getName()));

        return medicationEntity.getId();
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
