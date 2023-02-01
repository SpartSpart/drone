package com.spart.drone.repository.mapper;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.repository.model.ModelEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelDroneMapper {

    ModelDto toDto(ModelEntity modelEntity);

    ModelEntity toModel(ModelDto modelDto);

    List<ModelDto> toListDto(List<ModelEntity> modelEntityList);
}
