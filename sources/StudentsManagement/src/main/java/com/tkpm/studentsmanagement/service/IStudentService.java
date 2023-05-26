package com.tkpm.studentsmanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tkpm.studentsmanagement.dto.StudentDTO;

public interface IStudentService {
    public StudentDTO create(StudentDTO student);

    public List<StudentDTO> create(List<StudentDTO> listStudent);

    public List<StudentDTO> findAll(Pageable pageable);

    public List<StudentDTO> findAll();

    public List<StudentDTO> findAllById(List<Long> ids);

    public StudentDTO findById(Long id);

    public Integer totalPages(Pageable pageable);

    public Boolean delete(List<Long> ids);

    public Boolean update(StudentDTO student);
}
