package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.exception.DroneCountLimitException;
import com.spart.drone.exception.LoadWeightLimitException;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.DroneRepository;
import com.spart.drone.repository.mapper.DroneMapper;
import com.spart.drone.repository.model.DroneEntity;
import com.spart.drone.repository.model.MedicationEntity;
import com.spart.drone.repository.model.ModelEntity;
import com.spart.drone.repository.model.StateEntity;
import com.spart.drone.service.DroneService;
import com.spart.drone.service.MedicationService;
import com.spart.drone.service.ModelService;
import com.spart.drone.service.StateService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Primary
@Service
@Slf4j
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;
    private final ModelService modelService;
    private final StateService stateService;
    private final MedicationService medicationService;
    private final DroneMapper droneMapper;

    private final ModelMapper modelMapper;

    private final int MAX_VALUE_OF_DRONES_IN_SERVICE = 5;
    private final String LIMIT_COUNT_DRONE_MESSAGE = "Number of drones in database already equals ";
    private final Integer LOAD_WEIGHT_LIMIT = 500;

    /**
     * @param droneRepository
     * @param modelService
     * @param stateService
     * @param medicationService
     * @param droneMapper
     * @param modelMapper     */
    public DroneServiceImpl(DroneRepository droneRepository, ModelService modelService, StateService stateService, MedicationService medicationService, DroneMapper droneMapper, ModelMapper modelMapper) {
        this.droneRepository = droneRepository;
        this.modelService = modelService;
        this.stateService = stateService;
        this.medicationService = medicationService;
        this.droneMapper = droneMapper;

        this.modelMapper = modelMapper;
    }

    @Override
    public Long registerDrone(DroneDto droneDto) {
        DroneEntity droneEntity = modelMapper.map(droneDto, DroneEntity.class);

        checkNumberOfDrones();

        ModelEntity modelEntity = modelService.getModelIdByName(droneDto.getModel().getName());
        StateEntity stateEntity = stateService.getStateIdByName(droneDto.getState().getName());

        droneEntity.setModel(modelEntity);
        droneEntity.setState(stateEntity);

        return droneRepository.save(droneEntity).getId();
    }

    @Override
    public void loadMedication(Long droneId, Long medicationId)  {
        MedicationEntity medicationEntity = medicationService.getMedicationById(medicationId);
        DroneEntity droneEntity = droneRepository.getDroneById(droneId).orElseThrow(()->new NoSuchElementInDatabaseException(DroneEntity.class.getName()));
        if (isAbleToLoad(droneEntity,medicationEntity)) {
            droneEntity.addMedication(medicationEntity);
            droneRepository.save(droneEntity);
        }
        else
            throw new LoadWeightLimitException(LOAD_WEIGHT_LIMIT - droneLoadedMedicationWeight(droneEntity));
    }

    @Override
    public List<DroneDto> getAllDrones(Boolean availableOnly) {
        List<DroneDto> droneDtoList = new ArrayList<>();
        List<DroneEntity> droneEntityList = droneRepository.findAll();

        if (availableOnly)
            droneEntityList = droneEntityList.stream().filter(droneEntity -> isAbleToLoad(droneEntity))
                    .collect(Collectors.toList());

        droneEntityList.stream().forEach(droneEntity -> {
            DroneDto droneDto = droneMapper.toDto(droneEntity);
            droneDtoList.add(droneDto);
        });

        return droneDtoList;
    }

    @Override
    public List<MedicationDto> getDroneMedication(Long droneId) {
        Optional<DroneEntity> droneEntity = droneRepository.findById(droneId);
        if (droneEntity.isPresent())
            return droneMapper.toDto(droneEntity.get()).getMedicationDto();

        return null;
    }

    @Override
    public List<DroneDto> getDronesWithoutMedication() {
        List<DroneEntity> droneEntityList = droneRepository.findDronesWithoutMedication().orElse(null);
        if (droneEntityList != null)
            return droneMapper.toListDto(droneEntityList);
        return null;
    }



    private void checkNumberOfDrones(){
        if (droneRepository.count() == MAX_VALUE_OF_DRONES_IN_SERVICE)
            throw new DroneCountLimitException(LIMIT_COUNT_DRONE_MESSAGE + MAX_VALUE_OF_DRONES_IN_SERVICE);
    }

    private boolean isAbleToLoad(DroneEntity droneEntity, MedicationEntity medicationEntity){
        int droneLoadedMedicationWeight = droneLoadedMedicationWeight(droneEntity);
        int loadingMedicationWeight = medicationEntity.getWeight();

        if ((droneLoadedMedicationWeight + loadingMedicationWeight) <= LOAD_WEIGHT_LIMIT)
            return true;
        return false;
    }

    private boolean isAbleToLoad(DroneEntity droneEntity){
        int droneLoadedMedicationWeight = droneLoadedMedicationWeight(droneEntity);
        if (droneLoadedMedicationWeight < LOAD_WEIGHT_LIMIT)
            return true;
        return false;
    }

    private int droneLoadedMedicationWeight(DroneEntity droneEntity){
        return droneEntity.getMedicationEntities().stream()
                .mapToInt(m -> m.getWeight()).sum();
    }
}
