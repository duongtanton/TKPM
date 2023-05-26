package com.tkpm.studentsmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<StudentDTO> create(List<StudentDTO> listStudent) {
        List<StudentEntity> listStudentEntity = modelMapper.map(listStudent, new TypeToken<List<StudentEntity>>() {
        }.getType());
        List<StudentDTO> listStudentDto = modelMapper.map(studentRepositoty.saveAll(listStudentEntity),
                new TypeToken<List<StudentDTO>>() {
                }.getType());
        return listStudentDto;
    }

    @Override
    public List<StudentDTO> findAll(Pageable pageable) {
        List<StudentEntity> listStudentEntity = studentRepositoty.findAll(pageable);
        return modelMapper.map(listStudentEntity, new TypeToken<List<StudentDTO>>() {
        }.getType());
    }

    @Override
    public List<StudentDTO> findAll() {
        List<StudentEntity> listStudentEntity = studentRepositoty.findAll();
        return modelMapper.map(listStudentEntity, new TypeToken<List<StudentDTO>>() {
        }.getType());
    }

    @Override
    public List<StudentDTO> findAllById(List<Long> ids) {
        List<StudentEntity> listStudentEntitie = modelMapper.map(studentRepositoty.findAllById(ids),
                new TypeToken<List<StudentEntity>>() {
                }.getType());
        List<StudentDTO> listStudentEntity = modelMapper.map(listStudentEntitie, new TypeToken<List<StudentDTO>>() {
        }.getType());
        return listStudentEntity;
    }

    @Override
    public Integer totalPages(Pageable pageable) {
        Long count = studentRepositoty.count();
        Integer totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return totalPages;
    }

    @Override
    public Boolean delete(List<Long> ids) {
        try {
            studentRepositoty.deleteAllById(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public StudentDTO findById(Long id) {
        StudentEntity studentEntity = studentRepositoty.findById(id).orElse(null);
        return modelMapper.map(studentRepositoty.save(studentEntity), StudentDTO.class);
    }

    @Override
    public Boolean update(StudentDTO studentDTO) {
        StudentEntity studentEntity = studentRepositoty.findById(studentDTO.getId()).orElse(null);
        try {
            studentEntity.setName(studentDTO.getName());
            studentRepositoty.save(studentEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}