package com.spart.drone.controller.dto.drone;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.controller.dto.StateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DroneDto {

    @Size(max = 100)
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String serialNumber;

    @NotNull
    private ModelDto model;

    @Max(500)
    @Min(1)
    @NotNull
    private int weight;

    @Max(100)
    @Min(1)
    @NotNull
    private int batteryСapacity;

    @NotNull
    private StateDto state;

    private MedicationDto medicationDto;
}
