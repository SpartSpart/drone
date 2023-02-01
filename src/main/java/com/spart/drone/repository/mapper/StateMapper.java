package com.spart.drone.repository.mapper;

import com.spart.drone.controller.dto.StateDto;
import com.spart.drone.repository.model.StateEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StateMapper {

    StateDto toDto(StateEntity stateEntity);

    StateEntity toModel(StateDto stateDto);

    List<StateDto> toDtoList(List<StateEntity> stateEntityList);
}
