package com.tkpm.studentsmanagement.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.entity.StudentEntity;
import com.tkpm.studentsmanagement.repository.StudentRepositoty;
import com.tkpm.studentsmanagement.service.IStudentService;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentRepositoty studentRepositoty;

    public StudentDTO create(StudentDTO student) {
        StudentEntity studentEntity = modelMapper.map(student, StudentEntity.class);
        return modelMapper.map(studentRepositoty.save(studentEntity), StudentDTO.class);
    }
}