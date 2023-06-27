package com.tkpm.studentsmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.tkpm.studentsmanagement.entity.StudentEntity;

public interface StudentRepositoty extends CrudRepository<StudentEntity, Long> {
    List<StudentEntity> findAll(Pageable pageable);

    List<StudentEntity> findAll();

    List<StudentEntity> findByIdOrNameContainingOrEmailContaining(Long id, String name, String email,
            Pageable pageable);

}
