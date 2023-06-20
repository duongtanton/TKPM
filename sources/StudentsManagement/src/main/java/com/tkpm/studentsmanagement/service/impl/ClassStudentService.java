package com.tkpm.studentsmanagement.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.ClassStudentDTO;
import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.entity.ClassStudentEntity;
import com.tkpm.studentsmanagement.entity.StudentEntity;
import com.tkpm.studentsmanagement.repository.ClassStudentRepository;
import com.tkpm.studentsmanagement.service.IClassStudentService;

@Service
@Transactional
public class ClassStudentService implements IClassStudentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClassStudentRepository classStudentRepository;

    @Override
    public ClassStudentDTO create(ClassStudentDTO classStudentDTO) {
        ClassStudentEntity classStudentEntity = modelMapper.map(classStudentDTO, ClassStudentEntity.class);
        return modelMapper.map(classStudentRepository.save(classStudentEntity), ClassStudentDTO.class);
    }

    @Override
    public List<ClassStudentDTO> findByClasssId(Long classId, Pageable pageable) {
        List<ClassStudentEntity> classStudentEntities = classStudentRepository.findByClasssId(classId, pageable);

        List<ClassStudentDTO> classStudentDTOs = modelMapper.map(classStudentEntities,
                new TypeToken<List<ClassStudentDTO>>() {
                }.getType());
        for (int i = 0; i < classStudentDTOs.size(); i++) {
            classStudentDTOs.get(i)
                    .setStudent(modelMapper.map(classStudentEntities.get(i).getStudent(), StudentDTO.class));
            classStudentDTOs.get(i)
                    .setClasss(modelMapper.map(classStudentEntities.get(i).getClasss(), ClassDTO.class));
        }
        return classStudentDTOs;
    }

    public ClassStudentDTO findById(Long id) {
        ClassStudentEntity classStudentEntity = classStudentRepository.findById(id).orElse(null);

        ClassStudentDTO classStudentDTO = modelMapper.map(classStudentEntity, ClassStudentDTO.class);

        return classStudentDTO;
    }

    @Override
    public Boolean delete(List<Long> ids) {
        try {
            classStudentRepository.deleteAllById(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean update(ClassStudentDTO classStudentDTO) {
        ClassStudentEntity classStudentEntity = classStudentRepository.findById(classStudentDTO.getId()).orElse(null);
        try {
            classStudentEntity.setClasss(modelMapper.map(classStudentEntity.getClass(), ClassEntity.class));
            classStudentEntity.setStudent(modelMapper.map(classStudentEntity.getStudent(), StudentEntity.class));
            classStudentRepository.save(classStudentEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
