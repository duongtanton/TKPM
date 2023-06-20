package com.tkpm.studentsmanagement.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.ClassStudentDTO;

public interface IClassService {
    public void autoCreateClass();

    public ClassDTO findByClasssId(Long classId);

    public ClassDTO findById(Long id);

    public ClassDTO findByClassName(String className);

    public List<ClassDTO> getAll();

    // public List<ClassDTO> getAll(Pageable pageable);
    public ClassDTO save(ClassDTO userDTO);

    public boolean update(ClassDTO userDTO);

    public List<ClassDTO> save(List<ClassDTO> userDTO);

    public ClassStudentDTO saveStudents(ClassStudentDTO classStudentDTO);

    public Boolean delete(List<Long> id);

    public List<ClassDTO> findLikeByIdOrNameOrYear(Long id, String name, String year, Pageable pageable);
}
