package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.repository.model.ModelEntity;
import com.spart.drone.repository.ModelRepository;
import com.spart.drone.service.ModelService;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public Long saveModel(ModelDto modelDto){
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setModel(modelDto.getModel());
        return modelRepository.save(modelEntity).getId();

    }

}
