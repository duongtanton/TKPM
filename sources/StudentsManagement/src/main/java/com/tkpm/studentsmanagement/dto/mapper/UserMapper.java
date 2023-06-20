package com.tkpm.studentsmanagement.dto.mapper;

import com.tkpm.studentsmanagement.dto.UserDTO;
import com.tkpm.studentsmanagement.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(target = "createdBy",ignore = true)
    @Mapping(target = "updatedBy",ignore = true)
    @Mapping(target = "createdConfigurations",ignore = true)
    @Mapping(target = "updatedConfigurations",ignore = true)
    UserDTO toDto(UserEntity userEntity);
}
