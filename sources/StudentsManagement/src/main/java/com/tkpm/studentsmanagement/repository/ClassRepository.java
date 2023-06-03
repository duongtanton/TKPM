package com.tkpm.studentsmanagement.repository;

import com.tkpm.studentsmanagement.entity.ClassEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClassRepository extends CrudRepository<ClassEntity, Long> {
}
