package com.spart.drone.controller.dto.drone;

import com.spart.drone.controller.dto.MedicationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DroneLoadMedicationDto {

    @Size(max = 100)
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private MedicationDto medicationDto;
}
