package com.tkpm.studentsmanagement.service.impl;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.UserDTO;
import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.repository.ClassRepository;
import com.tkpm.studentsmanagement.service.IClassService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassService implements IClassService {

    @Autowired private ClassRepository classRepository;
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

    @Override
    public ClassDTO save(ClassDTO classDTO) {
        ClassEntity newClass = new ClassEntity();
        newClass.setName(classDTO.getName());
        newClass.setNumberOfPupils(classDTO.getNumberOfPupils());
        return modelMapper.map(classRepository.save(newClass), ClassDTO.class);
    }
}
