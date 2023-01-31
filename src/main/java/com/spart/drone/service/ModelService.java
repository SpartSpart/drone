package com.spart.drone.service;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.model.ModelEntity;
import org.springframework.stereotype.Service;

@Service
public interface ModelService {
    Long saveModel(ModelDto modelDto);

    ModelEntity getModelIdByName(String name) throws NoSuchElementInDatabaseException;

    ModelEntity getModelById(Long id);

}
