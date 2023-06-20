package com.tkpm.studentsmanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tkpm.studentsmanagement.dto.ConfigurationDTO;
import com.tkpm.studentsmanagement.dto.ConfigurationSearchDTO;
import com.tkpm.studentsmanagement.dto.SimpleResponse;

public interface IConfigurationService {
    List<ConfigurationDTO> findAll();
    SimpleResponse<ConfigurationDTO> findAll(ConfigurationSearchDTO configurationDTO, Pageable pageable);
    ConfigurationDTO findById(Long id);
    ConfigurationDTO findByName(String name);
    ConfigurationDTO save(ConfigurationDTO configurationDTO);
    ConfigurationDTO save(ConfigurationDTO configurationDTO, Long currentUserId);
    Boolean update(ConfigurationDTO configurationDTO, Long currentUserId);
    Boolean remove(Long id);
    Integer totalPages(Pageable pageable);
}
