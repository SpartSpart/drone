package com.spart.drone.service.validator;

import com.spart.drone.exception.DroneCountLimitException;
import com.spart.drone.repository.DroneRepository;
import com.spart.drone.repository.model.DroneEntity;
import com.spart.drone.repository.model.MedicationEntity;
import com.spart.drone.repository.model.ModelEntity;
import com.spart.drone.service.ModelService;
import com.spart.drone.service.helper.DroneState;
import com.spart.drone.service.status.Code;
import com.spart.drone.service.status.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DroneValidator {

    @Value("${drone.max.count}")
    private String DRONE_MAX_COUNT;

    @Value("${drone.battery.capacity.limit}")
    private String BATTERY_CAPACITY_LIMIT;

    private final String VALIDATION_FAILED = "Validation is failed: ";

    private final DroneRepository droneRepository;
    private final ModelService modelService;

    public DroneValidator(DroneRepository droneRepository,
                          ModelService modelService) {
        this.droneRepository = droneRepository;
        this.modelService = modelService;
    }

    public void checkNumberOfDrones(){
        if (droneRepository.count() == Integer.parseInt(DRONE_MAX_COUNT)) {
            log.warn(VALIDATION_FAILED + ValidationMessages.LIMIT_NUMBER_DRONE_MESSAGE + DRONE_MAX_COUNT);
            throw new DroneCountLimitException(ValidationMessages.LIMIT_NUMBER_DRONE_MESSAGE + DRONE_MAX_COUNT);
        }
    }

    public boolean isDroneValidForLoading(DroneEntity droneEntity){
        if (isValidToLoadByWeight(droneEntity)
                && isValidToLoadByBatteryLevel(droneEntity)
                && isValidToLoadByState(droneEntity))
            return true;
        return false;
    }

    public Response isDroneValidForLoadingByMedication(DroneEntity droneEntity, MedicationEntity medicationEntity){
        Response response = new Response();
        if (!isValidToLoadByWeight(droneEntity,medicationEntity))
            response.addError(ValidationMessages.LIMIT_LOAD_WEIGHT.toString());
        if(!isValidToLoadByBatteryLevel(droneEntity))
            response.addError(ValidationMessages.LOW_BATTERY_LEVEL + BATTERY_CAPACITY_LIMIT);
        if(!isValidToLoadByState(droneEntity))
            response.addError(ValidationMessages.DRONE_IS_IN_LOADING_STATE.toString());

        if(response.getStatus().getErrors() == null)
            response.getStatus().setCode(Code.ok);
        else {
            response.getStatus().setCode(Code.error);
            for (String error:response.getStatus().getErrors())
                log.warn(VALIDATION_FAILED + error);
        }

        return response;
    }

    private Boolean isValidToLoadByWeight(DroneEntity droneEntity, MedicationEntity medicationEntity){
        int droneLoadedMedicationWeight = droneLoadedMedicationWeight(droneEntity);
        int loadingMedicationWeight = medicationEntity.getWeight();

        if ((droneLoadedMedicationWeight + loadingMedicationWeight) < getDroneLimitWeight(droneEntity))
            return true;
        return false;
    }

    private boolean isValidToLoadByState(DroneEntity droneEntity) {
        if (droneEntity.getState().getName().equals(DroneState.LOADING.toString()))
            return false;
        return true;
    }

    private boolean isValidToLoadByBatteryLevel(DroneEntity droneEntity) {
        if (droneEntity.getBatteryСapacity() >= Integer.parseInt(BATTERY_CAPACITY_LIMIT))
            return true;
        return false;
    }

    private boolean isValidToLoadByWeight(DroneEntity droneEntity){
        int droneLoadedMedicationWeight = droneLoadedMedicationWeight(droneEntity);
        if (droneLoadedMedicationWeight < getDroneLimitWeight(droneEntity))
            return true;
        return false;
    }

    private int droneLoadedMedicationWeight(DroneEntity droneEntity){
        return droneEntity.getMedicationEntities().stream()
                .mapToInt(m -> m.getWeight()).sum();
    }

    private int getDroneLimitWeight(DroneEntity droneEntity){
        ModelEntity modelEntity = modelService.getModelById(droneEntity.getModel().getId());
        return modelEntity.getWeightLimit();
    }
}