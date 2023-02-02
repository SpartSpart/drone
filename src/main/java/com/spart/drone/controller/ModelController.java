package com.spart.drone.controller;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.service.ModelService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
public class ModelController {

    private static final String MODEL_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/model";

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping(MODEL_BASE_PATH)
    public Long saveModel(@RequestBody @Valid ModelDto modelDto) {
        return modelService.saveModel(modelDto);
    }

    @GetMapping(MODEL_BASE_PATH)
    public List<ModelDto> getAllModels() {
        return modelService.getAllModel();
    }
}
