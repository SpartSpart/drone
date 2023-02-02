package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.drone.DroneUpdateBatteryCapacityDto;
import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.controller.dto.drone.DroneUpdateStateDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.DroneRepository;
import com.spart.drone.repository.mapper.DroneMapper;
import com.spart.drone.repository.mapper.StateMapper;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;
    private final ModelService modelService;
    private final StateService stateService;
    private final MedicationService medicationService;
    private final DroneMapper droneMapper;
    private final DroneValidator droneValidator;
    private final StateMapper stateMapper;

    @Override
    @Transactional
    public Long addDrone(DroneDto droneDto) {

        DroneEntity droneEntity = droneMapper.toModel(droneDto); //modelMapper.map(droneDto, DroneEntity.class);

        droneValidator.checkNumberOfDrones();

        ModelEntity modelEntity = modelService.getModelIdByName(droneDto.getModel().getName());
        StateEntity stateEntity = stateService.getStateByName(droneDto.getState().getName());

        droneEntity.setModel(modelEntity);
        droneEntity.setState(stateEntity);

        droneEntity = droneRepository.save(droneEntity);
        log.info(String.format("Drone %s has been added", droneEntity.getName()));
        return droneEntity.getId();
    }

    @Override
    @Transactional
    public Response loadMedication(Long droneId, Long medicationId) {
        MedicationEntity medicationEntity = medicationService.getMedicationById(medicationId);
        DroneEntity droneEntity = droneRepository.getDroneById(droneId).orElseThrow(() -> new NoSuchElementInDatabaseException(DroneEntity.class.getName()));

        Response response = droneValidator.isDroneValidForLoadingByMedication(droneEntity, medicationEntity);

        if (response.getStatus().getCode().equals(Code.ok)) {
            changeDroneState(droneEntity, DroneState.LOADING);

            droneEntity.addMedication(medicationEntity);
            droneRepository.save(droneEntity);

            changeDroneState(droneEntity, DroneState.LOADED);
            log.info(String.format("Medication %s loaded to drone %s", medicationEntity.getName(), droneEntity.getName()));
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
        else throw new NoSuchElementInDatabaseException(DroneEntity.class.getName());
    }

    @Override
    @Transactional
    public List<DroneDto> getDronesWithoutMedication() {
        List<DroneEntity> droneEntityList = droneRepository.findDronesWithoutMedication().orElse(null);
        if (droneEntityList != null)
            return droneMapper.toListDto(droneEntityList);
        return null;
    }

    @Override
    public Integer getDronesBatteryLevel(Long droneId) {
        DroneEntity droneEntity = Optional.ofNullable(droneRepository.findById(droneId))
                .get()
                .orElseThrow(() -> new NoSuchElementInDatabaseException(DroneEntity.class.getName()));
        return droneEntity.getBatteryСapacity();
    }

    @Override
    public void getBatteryLevelStatistics() {
        List<DroneEntity> droneEntityList = droneRepository.findAll();
        log.info("Battery level Statistics:");
        log.info("==========================");
        droneEntityList.stream().forEach(droneEntity -> {
            if (isValidToLoadByBatteryLevel(droneEntity))
                log.info("{}, id={}, batteryLevel={}", droneEntity.getName(), droneEntity.getId(), droneEntity.getBatteryСapacity());
            else
                log.warn("{}, id={}, batteryLevel={}", droneEntity.getName(), droneEntity.getId(), droneEntity.getBatteryСapacity());
        });
        log.info("==========================");
    }

    @Override
    @Transactional
    public DroneDto getDroneById(Long droneId) {
        DroneEntity droneEntity = droneRepository.findById(droneId)
                .orElseThrow(() -> new NoSuchElementInDatabaseException(DroneEntity.class.getName()));
        return droneMapper.toDto(droneEntity);
    }

    @Override
    @Transactional
    public Long setBatteryLevel(DroneUpdateBatteryCapacityDto droneUpdateBatteryCapacityDto) {
        DroneEntity droneEntity = droneRepository.findById(droneUpdateBatteryCapacityDto.getId())
                .orElseThrow(() -> new NoSuchElementInDatabaseException(DroneEntity.class.getName()));
        droneEntity.setBatteryСapacity(droneUpdateBatteryCapacityDto.getBatteryСapacity());
        return droneRepository.save(droneEntity).getId();
    }

    @Override
    @Transactional
    public Long deleteDrone(Long droneId) {
        DroneEntity droneEntity = droneRepository.findById(droneId).orElseThrow(() -> new NoSuchElementInDatabaseException(DroneEntity.class.getName()));
        droneRepository.delete(droneEntity);
        return droneId;
    }

    @Override
    @Transactional
    public Long deleteMedicationFromDrone(Long droneId) {
        DroneEntity droneEntity = droneRepository.findById(droneId).orElseThrow(() -> new NoSuchElementInDatabaseException(DroneEntity.class.getName()));
        droneEntity.setMedicationEntities(null);
        StateEntity stateEntity = stateService.getStateByName(DroneState.IDLE.toString());
        droneEntity.setState(stateEntity);
        return droneRepository.save(droneEntity).getId();
    }

    @Override
    @Transactional
    public Long setState(DroneUpdateStateDto droneUpdateStateDto) {
        DroneEntity droneEntity = droneRepository.findById(droneUpdateStateDto.getId())
                .orElseThrow(() -> new NoSuchElementInDatabaseException(DroneEntity.class.getName()));
        droneEntity.setState(stateMapper.toModel(droneUpdateStateDto.getState()));
        return droneRepository.save(droneEntity).getId();
    }

    private void changeDroneState(DroneEntity droneEntity, DroneState droneState) {
        StateEntity stateEntity = stateService.getStateByName(droneState.toString());
        droneEntity.setState(stateEntity);
    }

    private boolean isValidToLoadByBatteryLevel(DroneEntity droneEntity) {
        return droneValidator.isValidToLoadByBatteryLevel(droneEntity);
    }
}
