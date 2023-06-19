package com.tkpm.studentsmanagement.repository;

import com.tkpm.studentsmanagement.dto.ClassStudentDTO;
import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.entity.ClassStudentEntity;
import com.tkpm.studentsmanagement.entity.StudentEntity;
import com.tkpm.studentsmanagement.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ClassStudentRepository extends CrudRepository<ClassStudentEntity, Long> {
    @Query("SELECT c_s FROM class_student c_s WHERE c_s.classEntity = :classEntity AND c_s.studentEntity = :studentEntity")
    List<ClassStudentEntity> findStudentInClass(@Param("classEntity") ClassEntity classEntity, @Param("studentEntity") StudentEntity studentEntity);

    List<ClassStudentEntity> findByClassEntityId(Long classId, Pageable pageable);
}
