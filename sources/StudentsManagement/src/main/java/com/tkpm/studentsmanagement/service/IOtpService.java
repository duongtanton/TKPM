package com.tkpm.studentsmanagement.service;

import com.tkpm.studentsmanagement.dto.OtpDTO;

public interface IOtpService {
    public OtpDTO save(OtpDTO otpDTO);
    public OtpDTO findByIdAndUsed(Long id, Boolean used);
}
