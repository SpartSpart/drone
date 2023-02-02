package com.spart.drone.repository.mapper;

import com.spart.drone.controller.dto.drone.DroneDto;
import com.spart.drone.repository.model.DroneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StateMapper.class, ModelDroneMapper.class, MedicationMapper.class})
public interface DroneMapper {

    @Mapping(source = "medicationEntities", target = "medicationDto")
    DroneDto toDto(DroneEntity droneEntity);

    DroneEntity toModel(DroneDto droneDto);

    @Mapping(source = "medicationEntities", target = "medicationDto")
    List<DroneDto> toListDto(List<DroneEntity> droneEntityList);
}
