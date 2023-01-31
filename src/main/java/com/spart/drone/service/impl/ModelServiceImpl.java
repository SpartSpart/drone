package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.model.ModelEntity;
import com.spart.drone.repository.ModelRepository;
import com.spart.drone.service.ModelService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Primary
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    @Transactional
    public Long saveModel(ModelDto modelDto){
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setName(modelDto.getName());
        return modelRepository.save(modelEntity).getId();
    }

    @Override
    @Transactional
    public ModelEntity getModelIdByName(String name){
        return Optional.ofNullable(modelRepository.findByName(name)).get().orElseThrow(()-> new NoSuchElementInDatabaseException(ModelEntity.class.getName()));
    }

    @Override
    @Transactional
    public ModelEntity getModelById(Long modelId) {
        return Optional.ofNullable(modelRepository.findById(modelId)).get().orElseThrow(()-> new NoSuchElementInDatabaseException(ModelEntity.class.getName()));
    }
}
