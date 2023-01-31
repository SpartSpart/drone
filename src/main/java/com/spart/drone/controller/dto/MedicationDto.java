package com.spart.drone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDto {

    private Long id;

    @Pattern(regexp = "[A-Za-z0-9_-]+")
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Min(1)
    private Integer weight;

    @Pattern(regexp = "[A-Z]+_[0-9]+")
    private String code;

    private ImageDto image;
}
