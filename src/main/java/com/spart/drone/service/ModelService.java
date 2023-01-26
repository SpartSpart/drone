package com.spart.drone.service;

import com.spart.drone.controller.dto.ModelDto;
import org.springframework.stereotype.Service;

@Service
public interface ModelService {
    public Long saveModel(ModelDto modelDto);
}
