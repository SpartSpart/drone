package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.DroneDto;
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
import com.spart.drone.service.helper.DroneState;
import com.spart.drone.service.status.Code;
import com.spart.drone.service.status.Response;
import com.spart.drone.service.validator.DroneValidator;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final DroneValidator droneValidator;

    /**
     * @param droneRepository
     * @param modelService
     * @param stateService
     * @param medicationService
     * @param droneMapper
     * @param droneValidator
     **/
    public DroneServiceImpl(DroneRepository droneRepository,
                            ModelService modelService,
                            StateService stateService,
                            MedicationService medicationService,
                            DroneMapper droneMapper,
                            DroneValidator droneValidator) {
        this.droneRepository = droneRepository;
        this.modelService = modelService;
        this.stateService = stateService;
        this.medicationService = medicationService;
        this.droneMapper = droneMapper;
        this.droneValidator = droneValidator;
    }

    @Override
    @Transactional
    public Long registerDrone(DroneDto droneDto) {

        DroneEntity droneEntity = droneMapper.toModel(droneDto); //modelMapper.map(droneDto, DroneEntity.class);

        droneValidator.checkNumberOfDrones();

        ModelEntity modelEntity = modelService.getModelIdByName(droneDto.getModel().getName());
        StateEntity stateEntity = stateService.getStateIdByName(droneDto.getState().getName());

        droneEntity.setModel(modelEntity);
        droneEntity.setState(stateEntity);

        return droneRepository.save(droneEntity).getId();
    }

    @Override
    @Transactional
    public Response loadMedication(Long droneId, Long medicationId)  {
        MedicationEntity medicationEntity = medicationService.getMedicationById(medicationId);
        DroneEntity droneEntity = droneRepository.getDroneById(droneId).orElseThrow(()->new NoSuchElementInDatabaseException(DroneEntity.class.getName()));

        Response response = droneValidator.isDroneValidForLoadingByMedication(droneEntity,medicationEntity);

        if (response.getStatus().getCode().equals(Code.ok)){
            changeDroneState(droneEntity, DroneState.LOADING);

            droneEntity.addMedication(medicationEntity);
            droneRepository.save(droneEntity);

            changeDroneState(droneEntity, DroneState.LOADED);
        }
        return response;
    }

    @Override
    @Transactional
    public List<DroneDto> getAllDrones(Boolean availableOnly) {
        List<DroneDto> droneDtoList = new ArrayList<>();
        List<DroneEntity> droneEntityList = droneRepository.findAll();

        if (availableOnly)
            droneEntityList = droneEntityList.stream().filter(droneEntity -> droneValidator.isDroneValidForLoading(droneEntity))
                    .collect(Collectors.toList());

        droneEntityList.stream().forEach(droneEntity -> {
            DroneDto droneDto = droneMapper.toDto(droneEntity);
            droneDtoList.add(droneDto);
        });

        return droneDtoList;
    }

    @Override
    @Transactional
    public List<MedicationDto> getDroneMedication(Long droneId) {
        Optional<DroneEntity> droneEntity = droneRepository.findById(droneId);
        if (droneEntity.isPresent())
            return droneMapper.toDto(droneEntity.get()).getMedicationDto();

        return null;
    }

    @Override
    @Transactional
    public List<DroneDto> getDronesWithoutMedication() {
        List<DroneEntity> droneEntityList = droneRepository.findDronesWithoutMedication().orElse(null);
        if (droneEntityList != null)
            return droneMapper.toListDto(droneEntityList);
        return null;
    }

    private void changeDroneState(DroneEntity droneEntity, DroneState droneState){
        StateEntity stateEntity = stateService.getStateIdByName(droneState.toString());
        droneEntity.setState(stateEntity);
    }
}
