package com.tkpm.studentsmanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tkpm.studentsmanagement.dto.ClassStudentDTO;
import com.tkpm.studentsmanagement.dto.StudentDTO;

public interface IClassStudentService {
    public ClassStudentDTO create(ClassStudentDTO classStudentDTO);

    public List<ClassStudentDTO> findByClassId(Long classId, Pageable pageable);

}
