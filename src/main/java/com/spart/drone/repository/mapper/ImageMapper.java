package com.spart.drone.repository.mapper;

import com.spart.drone.controller.dto.ImageDto;
import com.spart.drone.repository.model.ImageEntity;
import org.mapstruct.Mapper;
import java.io.IOException;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    ImageDto toDto(ImageEntity imageEntity);

    ImageEntity toModel(ImageDto imageDto) throws IOException;
}
