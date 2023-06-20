package com.tkpm.studentsmanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.dto.OtpDTO;
import com.tkpm.studentsmanagement.entity.OtpEntity;
import com.tkpm.studentsmanagement.repository.OtpRepositopy;
import com.tkpm.studentsmanagement.service.IOtpService;

import jakarta.transaction.Transactional;

@Service
@Transactional
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
    @Override
    public OtpDTO findByIdAndUsed(Long id, Boolean used) {
        OtpEntity otpEntity =  otpRepositopy.findByIdAndUsed(id, used);
        return  modelMapper.map(otpEntity, OtpDTO.class);
    }
    @Override
    public void markToUsed(Long id) {
        OtpEntity otpEntity =  otpRepositopy.findByIdAndUsed(id, false);
        otpEntity.setUsed(true);
        otpRepositopy.save(otpEntity);
    }
}
