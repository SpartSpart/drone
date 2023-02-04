package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.exception.ConstraintDatabaseException;
import com.spart.drone.repository.StateRepository;
import com.spart.drone.repository.mapper.StateMapper;
import com.spart.drone.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(value = "classpath:test_data_h2.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:clean_data_h2.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StateServiceImplTest {

    @Autowired
    StateRepository stateRepository;

    @Autowired
    StateService stateService;

    @Autowired
    StateMapper stateMapper;

    private StateDto stateDtoIsAlreadyExistsInDB;
    private StateDto stateDtoIsNotExistsInDB;

    @BeforeEach
    public void init() {
        stateDtoIsAlreadyExistsInDB = new StateDto(null, "LOADING");
        stateDtoIsNotExistsInDB = new StateDto(10L,"AbsolutelyNewState");
    }


    @Test
    public void saveState_sendDtoWhichAlreadyExistsInDatabase_returnConstraintDatabaseException() {
        Throwable thrown = catchThrowable(() ->
                stateService.addState(stateDtoIsAlreadyExistsInDB));

        assertThat(thrown).isInstanceOf(ConstraintDatabaseException.class);
    }


    @Test
    public void getAllStates_deleteState_returnAllStates() {
        List<StateDto> stateDtoList = stateService.getAllStates();

        assertEquals(stateDtoList.size(), 6);

        stateRepository.deleteById(6L);

        stateDtoList = stateService.getAllStates();

        assertEquals(stateDtoList.size(), 5);
        assertEquals(stateDtoList.get(0).getName(),"DELIVERED");
    }
}