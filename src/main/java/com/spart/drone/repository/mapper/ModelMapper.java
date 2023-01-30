package com.spart.drone.repository.mapper;

import com.spart.drone.controller.dto.ModelDto;
import com.spart.drone.repository.model.ModelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    ModelDto toDto(ModelEntity modelEntity);

    ModelEntity toModel(ModelDto modelDto);
}
