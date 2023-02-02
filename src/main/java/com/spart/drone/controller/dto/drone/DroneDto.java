package com.spart.drone.controller.dto.drone;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.controller.dto.StateDto;
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

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String serialNumber;

    @NotNull
    private ModelDto model;

    @Max(100)
    @Min(0)
    @NotNull
    private int batteryСapacity;

    @NotNull
    private StateDto state;

    private List<MedicationDto> medicationDto;
}
