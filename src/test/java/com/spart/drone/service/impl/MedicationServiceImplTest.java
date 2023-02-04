package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.MedicationRepository;
import com.spart.drone.repository.mapper.MedicationMapper;
import com.spart.drone.repository.model.MedicationEntity;
import com.spart.drone.service.MedicationService;
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
public class MedicationServiceImplTest {

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    MedicationMapper medicationMapper;

    @Autowired
    MedicationService medicationService;

    @Test
    public void getMedicationById_whenMedicationExistsInDB_thenReturnMedicationId(){
        MedicationEntity medicationEntity = medicationService.getMedicationById(1L);

        assertEquals(medicationEntity.getName(),"Aspirin_01-1");
        assertEquals(medicationEntity.getCode(),"AS_01");
    }

    @Test
    public void getMedicationById_whenMedicationNotExistsInDB_thenReturnNoSuchElementInDatabaseException(){
        Throwable thrown = catchThrowable(() ->
                        medicationService.getMedicationById(10L));

        assertThat(thrown).isInstanceOf(NoSuchElementInDatabaseException.class);
    }

    @Test
    public void getAllMedication_returnAllMedications(){
        List<MedicationDto> medicationDtoList = medicationService.getAllMedication();

        assertEquals(medicationDtoList.size(), 5);
        assertEquals(medicationDtoList.get(0).getName(), "Aspirin_01-1");
        assertEquals(medicationDtoList.get(0).getCode(),"AS_01");
    }
}