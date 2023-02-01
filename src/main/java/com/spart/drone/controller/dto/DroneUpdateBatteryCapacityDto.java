package com.spart.drone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DroneUpdateBatteryCapacityDto {

    private Long id;

    @Max(100)
    @Min(0)
    @NotNull
    private int batteryСapacity;
}
