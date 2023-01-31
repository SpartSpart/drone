package com.spart.drone.repository.mapper;

import com.spart.drone.controller.dto.MedicationDto;
import com.spart.drone.repository.model.MedicationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface MedicationMapper {

    MedicationDto toDto(MedicationEntity medicationEntity);

    MedicationEntity toModel(MedicationDto medicationDto);

    List<MedicationDto> toListDto(List<MedicationEntity> medicationEntityList);
}
