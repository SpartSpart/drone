package com.spart.drone.service;

import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.model.StateEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StateService {
    StateEntity getStateIdByName(String name) throws NoSuchElementInDatabaseException;

    Long addState(StateDto stateDto);

    List<StateDto> getAllStates();
}
