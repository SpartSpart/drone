package com.spart.drone.service;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.model.ModelEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModelService {
    Long saveModel(ModelDto modelDto);

    ModelEntity getModelByName(String name) throws NoSuchElementInDatabaseException;

    ModelEntity getModelById(Long id);

    List<ModelDto> getAllModel();
}
