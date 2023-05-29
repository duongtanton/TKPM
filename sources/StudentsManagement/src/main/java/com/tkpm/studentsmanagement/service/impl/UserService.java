package com.tkpm.studentsmanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.dto.UserDTO;
import com.tkpm.studentsmanagement.entity.UserEntity;
import com.tkpm.studentsmanagement.repository.UserRepository;
import com.tkpm.studentsmanagement.service.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO findByUsernameAndEmail(String username, String email) {

        UserEntity userEntity = userRepository.findByUsernameAndEmail(username, email);
        UserDTO userDTO = null;
        try {
            userDTO = modelMapper.map(userEntity, UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDTO;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        UserEntity userEntityRes = userRepository.save(userEntity);
        UserDTO userDTORes = modelMapper.map(userEntityRes, UserDTO.class);
        return userDTORes;
    }

    @Override
    public UserDTO findByUsername(String username) {
        UserEntity userEntityRes = userRepository.findByUsername(username);
        UserDTO userDTO = modelMapper.map(userEntityRes, UserDTO.class);
        return userDTO;
    }
}
