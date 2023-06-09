package com.tkpm.studentsmanagement.service.impl;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.dto.UserDTO;
import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.entity.ClassStudentEntity;
import com.tkpm.studentsmanagement.entity.StudentEntity;
import com.tkpm.studentsmanagement.repository.ClassRepository;
import com.tkpm.studentsmanagement.repository.ClassStudentRepository;
import com.tkpm.studentsmanagement.service.IClassService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassService implements IClassService {

    @Autowired private ClassRepository classRepository;
    @Autowired private ClassStudentRepository classStudentRepository;
    @Autowired private ModelMapper modelMapper;
    @Override
    public ClassDTO findByClassId(Long classId) {
        Optional<ClassEntity> optionalClassEntity = classRepository.findById(classId);
        ClassDTO classDTO = null;

        try {
            classDTO = modelMapper.map(optionalClassEntity, ClassDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classDTO;
    }

    @Override
    public ClassDTO findByClassName(String className) {
        return null;
    }

    @Override
    public List<ClassDTO> getAll() {
        List<ClassDTO> result = new ArrayList<>();
        classRepository.findAll().forEach(classEntity -> {
            result.add(modelMapper.map(classEntity, ClassDTO.class) );
        });
        return result;
    }

//    @Override
//    public List<ClassDTO> getAll(Pageable pageable) {
//        List<StudentEntity> listStudentEntity = classRepository.findAll(pageable);
//        return modelMapper.map(listStudentEntity, new TypeToken<List<StudentDTO>>() {
//        }.getType());
//    }

    @Override
    public ClassDTO save(ClassDTO classDTO) {
        ClassEntity newClass = new ClassEntity();
        if(classDTO.getId() != null) {
            newClass.setId(classDTO.getId());
        }
        newClass.setName(classDTO.getName());
        if(classDTO.getNumberOfPupils() != null) {
            newClass.setNumberOfPupils(classDTO.getNumberOfPupils());
        }
        return modelMapper.map(classRepository.save(newClass), ClassDTO.class);
    }
    @Override
    public List<ClassDTO> save(List<ClassDTO> listClass) {
        List<ClassEntity> listStudentEntity = modelMapper.map(listClass, new TypeToken<List<ClassEntity>>() {
        }.getType());
        List<ClassDTO> listClassDTO = modelMapper.map(classRepository.saveAll(listStudentEntity),
                new TypeToken<List<StudentDTO>>() {
                }.getType());
        return listClassDTO;
    }

    @Override
    public Boolean delete(List<Long> listClassIds) {
        try {
            classRepository.deleteAllById(listClassIds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
