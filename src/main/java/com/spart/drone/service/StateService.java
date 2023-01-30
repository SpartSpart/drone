package com.spart.drone.service;

import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.model.StateEntity;
import org.springframework.stereotype.Service;

@Service
public interface StateService {
    StateEntity getStateIdByName(String name) throws NoSuchElementInDatabaseException;
}
