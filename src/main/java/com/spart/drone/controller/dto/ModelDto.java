package com.spart.drone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ModelDto {

    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    private Integer weightLimit;
}
