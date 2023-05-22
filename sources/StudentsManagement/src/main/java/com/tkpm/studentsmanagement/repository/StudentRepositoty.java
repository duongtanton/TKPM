package com.tkpm.studentsmanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.tkpm.studentsmanagement.entity.StudentEntity;

public interface StudentRepositoty extends CrudRepository<StudentEntity, Long> {

}
