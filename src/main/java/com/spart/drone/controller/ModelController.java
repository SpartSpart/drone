package com.spart.drone.controller;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.service.impl.ModelServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/model")
public class ModelController {
    private final ModelServiceImpl modelService;

    public ModelController(ModelServiceImpl modelService) {
        this.modelService = modelService;
    }

    @PostMapping
    public ResponseEntity<Long> saveModel(@RequestBody ModelDto model){
        return ResponseEntity.ok().body(modelService.saveModel(model));
    }
}
