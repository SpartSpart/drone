package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.exception.DroneCountLimitException;
import com.spart.drone.exception.NoSuchElementInDatabase;
import com.spart.drone.repository.DroneRepository;
import com.spart.drone.repository.model.DroneEntity;
import com.spart.drone.repository.model.ModelEntity;
import com.spart.drone.repository.model.StateEntity;
import com.spart.drone.service.DroneService;
import com.spart.drone.service.ModelService;
import com.spart.drone.service.StateService;
import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;

@Primary
@Service
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;
    private final ModelService modelService;
    private final StateService stateService;

    private final ModelMapper modelMapper;

    private final int MAX_VALUE_OF_DRONES_IN_SERVICE = 5;
    private final String LIMIT_COUNT_DRONE_MESSAGE = "Number of drones in database already equals ";

    /**
     *  @param droneRepository
     * @param modelService
     * @param stateService
     * @param modelMapper*/
    public DroneServiceImpl(DroneRepository droneRepository, ModelService modelService, StateService stateService, ModelMapper modelMapper) {
        this.droneRepository = droneRepository;
        this.modelService = modelService;
        this.stateService = stateService;

        this.modelMapper = modelMapper;
    }

    @Override
    public Long registerDrone(DroneDto droneDto) throws NoSuchElementInDatabase, DroneCountLimitException {
        DroneEntity droneEntity = modelMapper.map(droneDto, DroneEntity.class);

        checkNumberOfDrones();

        ModelEntity modelEntity = modelService.getModelIdByName(droneDto.getModel().getName());
        StateEntity stateEntity = stateService.getStateIdByName(droneDto.getState().getName());

        droneEntity.setModel(modelEntity);
        droneEntity.setState(stateEntity);

        return droneRepository.save(droneEntity).getId();
    }

    private void checkNumberOfDrones() throws DroneCountLimitException {
        if (droneRepository.count() == MAX_VALUE_OF_DRONES_IN_SERVICE)
            throw new DroneCountLimitException(LIMIT_COUNT_DRONE_MESSAGE + MAX_VALUE_OF_DRONES_IN_SERVICE);
    }
}
