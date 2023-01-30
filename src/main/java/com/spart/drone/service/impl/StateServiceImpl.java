package com.spart.drone.service.impl;

import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.StateRepository;
import com.spart.drone.repository.model.StateEntity;
import com.spart.drone.service.StateService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public StateEntity getStateIdByName(String name)  {
        return Optional.ofNullable(stateRepository.findByName(name)).get().orElseThrow(()->new NoSuchElementInDatabaseException(StateEntity.class.getName()));
    }
}
