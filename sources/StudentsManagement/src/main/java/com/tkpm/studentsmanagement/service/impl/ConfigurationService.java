package com.tkpm.studentsmanagement.service.impl;

import com.tkpm.studentsmanagement.constant.Role;
import com.tkpm.studentsmanagement.dto.ConfigurationDTO;
import com.tkpm.studentsmanagement.dto.ConfigurationSearchDTO;
import com.tkpm.studentsmanagement.dto.SimpleResponse;
import com.tkpm.studentsmanagement.dto.UserDTO;
import com.tkpm.studentsmanagement.dto.mapper.ConfigurationMapper;
import com.tkpm.studentsmanagement.dto.mapper.UserMapper;
import com.tkpm.studentsmanagement.entity.Configuration;
import com.tkpm.studentsmanagement.repository.ConfigurationRepository;
import com.tkpm.studentsmanagement.repository.RoleRepository;
import com.tkpm.studentsmanagement.repository.UserRepository;
import com.tkpm.studentsmanagement.service.IConfigurationService;
import com.tkpm.studentsmanagement.util.TimestampUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigurationService implements IConfigurationService {
    @Autowired
    private ConfigurationRepository configurationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ConfigurationMapper configurationMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<ConfigurationDTO> findAll() {
        List<Configuration> configurations = configurationRepository.findAll();
        return configurations.stream().map(c -> configurationMapper.toDto(c)).collect(Collectors.toList());
    }

    @Override
    public SimpleResponse<ConfigurationDTO> findAll(ConfigurationSearchDTO configurationSearchDTO, Pageable pageable) {
        SimpleResponse<ConfigurationDTO> simpleResponse = new SimpleResponse<>();
        ConfigurationDTO configurationDTO = new ConfigurationDTO();
        configurationDTO.setCreatedDate(TimestampUtil.convertStringToTimestamp(configurationSearchDTO.getCreatedDate()));
        configurationDTO.setUpdatedDate(TimestampUtil.convertStringToTimestamp(configurationSearchDTO.getUpdatedDate()));
        Page<Configuration> configurationPage = configurationRepository.findAll(
                configurationSearchDTO.getText(),
                configurationDTO.getCreatedDate(),
                configurationDTO.getUpdatedDate(),
                configurationSearchDTO.getCreatedBy(),
                configurationSearchDTO.getUpdatedBy(),
                pageable);
        List<ConfigurationDTO> listT = configurationPage.get()
                .map(c -> configurationMapper.toDto(c)).collect(Collectors.toList());
        simpleResponse.setPerPage(pageable.getPageSize());
        simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
        simpleResponse.setTotalPages(totalPages(pageable));
        simpleResponse.setListT(listT);
        return simpleResponse;
    }

    @Override
    public ConfigurationDTO findById(Long id) {
        return configurationMapper.toDto(configurationRepository.findById(id).orElse(null));
    }

    @Override
    public ConfigurationDTO findByName(String name) {
        return configurationMapper.toDto(configurationRepository.findByName(name));
    }

    @Override
    public ConfigurationDTO save(ConfigurationDTO configurationDTO) {
        Configuration configurationEntity = modelMapper.map(configurationDTO, Configuration.class);
        Configuration configurationEntityRes = configurationRepository.save(configurationEntity);
        ConfigurationDTO configurationDTORes = configurationMapper.toDto(configurationEntityRes);
        update(configurationDTORes,configurationDTO.getUpdatedBy().getId());
        return configurationDTORes;
    }

    @Override
    public ConfigurationDTO save(ConfigurationDTO configurationDTO, Long currentUserId) {
        UserDTO currentUser = userMapper.toDto(userRepository.findById(currentUserId).orElse(null));
        if(currentUser == null || !currentUser.getRoles().contains(roleRepository.findByName(Role.ADMIN.name()))) {
            throw new RuntimeException(String.format("Invalid role. It should be  %s",
                    Arrays.asList(Role.values())));
        }
        ConfigurationDTO configuration = new ConfigurationDTO();
        BeanUtils.copyProperties(configurationDTO,configuration);
        configuration.setCreatedBy(currentUser);
        configuration.setUpdatedBy(currentUser);
        return save(configuration);
    }

    @Override
    public Boolean update(ConfigurationDTO configurationDTO, Long currentUserId) {
        Configuration configuration = configurationRepository.findById(configurationDTO.getId()).orElse(null);
        try {
            configuration.setValue(configurationDTO.getValue());
            configuration.setDescription(configurationDTO.getDescription());
            configuration.setUpdatedBy(userRepository.findById(currentUserId).orElse(null));
            configurationRepository.save(configuration);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean remove(Long id) {
        configurationRepository.deleteById(id);
        return true;
    }

    @Override
    public Integer totalPages(Pageable pageable) {
        Long count = configurationRepository.count();
        return (Integer) (int) Math.ceil((double) count / pageable.getPageSize());
    }
}
