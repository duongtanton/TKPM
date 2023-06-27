package com.tkpm.studentsmanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.tkpm.studentsmanagement.entity.OtpEntity;

public interface OtpRepositopy extends CrudRepository<OtpEntity, Long> {
  public OtpEntity findByIdAndUsed(Long id, Boolean used);
}
