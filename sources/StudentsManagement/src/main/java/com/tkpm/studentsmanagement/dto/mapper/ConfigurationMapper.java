package com.tkpm.studentsmanagement.dto.mapper;

import com.tkpm.studentsmanagement.dto.ConfigurationDTO;
import com.tkpm.studentsmanagement.entity.Configuration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ConfigurationMapper {
    @Mapping(target = "createdBy",ignore = true)
    @Mapping(target = "updatedBy",ignore = true)
    ConfigurationDTO toDto(Configuration configuration);
}
