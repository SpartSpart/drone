package com.spart.drone.service.impl;

import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.exception.ConstraintDatabaseException;
import com.spart.drone.exception.NoSuchElementInDatabaseException;
import com.spart.drone.repository.StateRepository;
import com.spart.drone.repository.mapper.StateMapper;
import com.spart.drone.repository.model.StateEntity;
import com.spart.drone.service.StateService;
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
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;

    @Override
    @Transactional
    public StateEntity getStateByName(String name) {
        return Optional.ofNullable(stateRepository.findByName(name)).get().orElseThrow(() -> new NoSuchElementInDatabaseException(StateEntity.class.getName()));
    }

    @Override
    @Transactional
    public Long addState(StateDto stateDto) {
        StateEntity stateEntity = null;
        try {
            stateEntity = stateRepository.save(stateMapper.toModel(stateDto));
        }catch (Exception e){
            throw new ConstraintDatabaseException();
        }
        log.info(String.format("State %s added", stateEntity.getName()));
        return stateEntity.getId();
    }

    @Override
    @Transactional
    public List<StateDto> getAllStates() {
        return stateMapper.toDtoList(stateRepository.findAll());
    }
}
