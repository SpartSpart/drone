package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.exception.ConstraintDatabaseException;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.mapper.ModelDroneMapper;
import com.spart.drone.repository.model.ModelEntity;
import com.spart.drone.repository.ModelRepository;
import com.spart.drone.service.ModelService;
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
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final ModelDroneMapper modelDroneMapper;

    @Override
    @Transactional
    public Long saveModel(ModelDto modelDto){
        ModelEntity modelEntity = null;
        try {
            modelEntity = modelRepository.save(modelDroneMapper.toModel(modelDto));
        }
        catch (Exception e){
            throw new ConstraintDatabaseException();
        }
        log.info(String.format("Model %s added", modelEntity.getName()));
        return modelEntity.getId();
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

    @Override
    public List<ModelDto> getAllModel() {
        return modelDroneMapper.toListDto(modelRepository.findAll());
    }
}
