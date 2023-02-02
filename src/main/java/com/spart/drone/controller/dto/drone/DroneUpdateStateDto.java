package com.spart.drone.controller.dto.drone;

import com.spart.drone.controller.dto.StateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DroneUpdateStateDto {

    private Long id;

    @NotNull
    private StateDto state;
}
