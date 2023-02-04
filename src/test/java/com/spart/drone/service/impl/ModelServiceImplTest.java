package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.exception.ConstraintDatabaseException;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.ModelRepository;
import com.spart.drone.repository.mapper.ModelDroneMapper;
import com.spart.drone.repository.model.ModelEntity;
import com.spart.drone.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
public class ModelServiceImplTest {

    @MockBean
    ModelRepository modelRepository;

    @Autowired
    ModelService modelService;

    @MockBean
    ModelDroneMapper modelDroneMapper;

    private ModelEntity modelEntity;

    @BeforeEach
    public void init(){
        modelEntity = new ModelEntity();
        modelEntity.setId(1L);
        modelEntity.setName("Model");
    }

    @Test
    public void saveModel_sendDataWhichIsNoInDatabase_returnId() {
        given(modelRepository.save(any())).willReturn(modelEntity);
        Long modelId = modelService.saveModel(new ModelDto());

        assertThat(modelId.equals(modelEntity.getId()));
    }

    @Test
    public void saveModel_sendDataWhichIsAlreadyInDatabase_returnException() {
        given(modelRepository.save(any())).willReturn(new Exception());
        Throwable thrown = catchThrowable(() ->
            modelService.saveModel(new ModelDto()));

        assertThat(thrown).isInstanceOf(ConstraintDatabaseException.class);
    }

    @Test
    public void getModelById_sendDataWhichIsNoInDatabase_returnException() {
        given(modelRepository.findById(any())).willReturn(Optional.empty());
        Throwable thrown = catchThrowable(() ->
                modelService.getModelById(1L));

        assertThat(thrown).isInstanceOf(NoSuchElementInDatabaseException.class);
    }

    @Test
    public void getModelById_sendDataWhichIsNoInDatabase_returnId() {
        given(modelRepository.findById(any())).willReturn(Optional.of(modelEntity));
        ModelEntity modelEntity = modelService.getModelById(1L);

        assertThat(modelEntity.equals(this.modelEntity));
    }

    @Test
    public void getModelByName_sendDataWhichIsAlreadyInDatabase_returnException() {
        given(modelRepository.findByName(any())).willReturn(Optional.empty());
        Throwable thrown = catchThrowable(() ->
                modelService.getModelById(1L));

        assertThat(thrown).isInstanceOf(NoSuchElementInDatabaseException.class);
    }

    @Test
    public void getModelByName_sendDataWhichIsAlreadyInDatabase_returnId() {
        given(modelRepository.findById(any())).willReturn(Optional.of(modelEntity));
        ModelEntity modelEntity = modelService.getModelById(1L);

        assertThat(modelEntity.equals(this.modelEntity));
    }

    @Test
    public void getAllModel_returnArrayOfModels() {
        given(modelRepository.findAll()).willReturn(new ArrayList<>(Arrays.asList(modelEntity)));

        assertThat(modelService.getAllModel().size() == 1);
    }
}