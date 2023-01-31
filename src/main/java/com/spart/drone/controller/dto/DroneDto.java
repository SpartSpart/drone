package com.spart.drone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DroneDto {

    private Long id;

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

    private List<MedicationDto> medicationDto;
}
