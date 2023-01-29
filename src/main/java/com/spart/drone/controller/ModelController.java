package com.spart.drone.controller;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.service.ModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Model controller allows working with drone models
 */
@RestController
@RequestMapping("/api/v1/model")
public class ModelController {
    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     *
     * @param modelDto
     * @return id model
     */
    @PostMapping()
    public ResponseEntity<Long> saveModel(@RequestBody ModelDto modelDto){
        return ResponseEntity.ok().body(modelService.saveModel(modelDto));
    }
}
