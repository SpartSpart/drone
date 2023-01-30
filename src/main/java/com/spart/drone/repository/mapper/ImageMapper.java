package com.spart.drone.repository.mapper;

import com.spart.drone.controller.dto.ImageDto;
import com.spart.drone.repository.model.ImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageDto toDto(ImageEntity imageEntity);

    ImageEntity toModel(ImageDto imageDto);
}
