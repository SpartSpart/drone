package com.spart.drone.controller;

import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.service.StateService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * State controller allows working with drone state
 */
@RestController
@RequestMapping("/api/v1/state")
public class StateController {
    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping
    public Long addState(@RequestBody @Valid StateDto stateDto){
        return stateService.addState(stateDto);
    }

    @GetMapping
    public List<StateDto> getAllStates(){
        return stateService.getAllStates();
    }
}
