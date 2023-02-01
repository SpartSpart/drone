package com.spart.drone.controller;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.service.ModelService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
     * @param modelDto
     * @return id model
     */
    @PostMapping()
    public Long saveModel(@RequestBody @Valid ModelDto modelDto) {
        return modelService.saveModel(modelDto);
    }

    @GetMapping
    public List<ModelDto> getAllModels() {
        return modelService.getAllModel();
    }
}
