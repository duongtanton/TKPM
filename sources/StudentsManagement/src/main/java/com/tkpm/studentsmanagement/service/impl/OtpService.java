package com.tkpm.studentsmanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.dto.OtpDTO;
import com.tkpm.studentsmanagement.entity.OtpEntity;
import com.tkpm.studentsmanagement.repository.OtpRepositopy;
import com.tkpm.studentsmanagement.service.IOtpService;

@Service
public class OtpService implements IOtpService {

    @Autowired
    private OtpRepositopy otpRepositopy;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OtpDTO save(OtpDTO otpDTO) {
        OtpEntity otpEntity = modelMapper.map(otpDTO, OtpEntity.class); 
        return modelMapper.map(otpRepositopy.save(otpEntity), OtpDTO.class);
    }
}
