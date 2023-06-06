package com.tkpm.studentsmanagement.repository;

import com.tkpm.studentsmanagement.entity.ClassStudentEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClassStudentRepository extends CrudRepository<ClassStudentEntity, Long> {
}
