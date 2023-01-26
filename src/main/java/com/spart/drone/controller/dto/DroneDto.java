package com.spart.drone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DroneDto {
    private Long id;
    private String serialNumber;
    private ModelDto model;
    private int weight;
    private int batteryСapacity;
    private StateDto state;
}
