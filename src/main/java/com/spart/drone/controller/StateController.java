package com.spart.drone.controller;

import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.service.StateService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping()
public class StateController {

    private static final String STATE_BASE_PATH = ControllerConfiguration.APPLICATION_V1_PATH + "/state";

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping(STATE_BASE_PATH)
    public Long addState(@RequestBody @Valid StateDto stateDto){
        return stateService.addState(stateDto);
    }

    @GetMapping(STATE_BASE_PATH)
    public List<StateDto> getAllStates(){
        return stateService.getAllStates();
    }
}
