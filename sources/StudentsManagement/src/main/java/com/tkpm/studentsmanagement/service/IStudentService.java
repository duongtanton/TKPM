package com.tkpm.studentsmanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tkpm.studentsmanagement.dto.StudentDTO;

public interface IStudentService {
    public StudentDTO create(StudentDTO student);
    public List<StudentDTO> findAll(Pageable pageable);
    public List<StudentDTO> findAll();
    public StudentDTO findById(Long id);
    public Integer totalPages(Pageable pageable);
    public Boolean delete(List<Long> ids);
    public Boolean update(StudentDTO studentDTO);
}
