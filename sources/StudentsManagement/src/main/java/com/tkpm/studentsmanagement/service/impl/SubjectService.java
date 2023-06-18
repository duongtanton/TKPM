package com.tkpm.studentsmanagement.service.impl;

import com.tkpm.studentsmanagement.dto.SubjectDTO;
import com.tkpm.studentsmanagement.entity.SubjectEntity;
import com.tkpm.studentsmanagement.repository.SubjectRepository;
import com.tkpm.studentsmanagement.service.ISubjectService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/
@Service
@Transactional
public class SubjectService implements ISubjectService {
    private ModelMapper modelMapper;
    SubjectRepository subjectRepository;
    @Override
    public SubjectDTO create(SubjectDTO subject) {
        SubjectEntity subjectEntity = modelMapper.map(subject, SubjectEntity.class);
        return modelMapper.map(subjectRepository.save(subjectEntity), SubjectDTO.class);
    }

    @Override
    public List<SubjectDTO> create(List<SubjectDTO> listSubject) {
        List<SubjectEntity> listSubjectEntity = modelMapper.map(
                listSubject,
                new TypeToken<List<SubjectEntity>>(){}.getType());

        List<SubjectDTO> listSubjectDTO = modelMapper.map(
                subjectRepository.saveAll(listSubjectEntity),
                new TypeToken<List<SubjectDTO>>(){}.getType());
        return listSubjectDTO;
    }

    @Override
    public List<SubjectDTO> findAll(java.awt.print.Pageable pageable) {
        return null;
    }

    @Override
    public List<SubjectDTO> findAll(Pageable pageable) {
        List<SubjectEntity> listSubjectEntity = subjectRepository.findAll(pageable);
        return modelMapper.map(
                listSubjectEntity,
                new TypeToken<List<SubjectEntity>>(){}.getType());
    }

    @Override
    public List<SubjectDTO> findAll() {
        List<SubjectEntity> listSubjectEntity = subjectRepository.findAll();
        return modelMapper.map(
                listSubjectEntity,
                new TypeToken<List<SubjectEntity>>(){}.getType());
    }

    @Override
    public SubjectDTO findByID(Long subjectID) {
        return null;
    }

    @Override
    public Integer totalPages(java.awt.print.Pageable pageable) {
        return null;
    }

    @Override
    public Integer totalPages(Pageable pageable) {
        Long count = subjectRepository.count();
        Integer totalPages = (int) Math.ceil((double) count / pageable.getPageSize());
        return totalPages;
    }

    @Override
    public Boolean delete(List<Long> ids) {
        try {
            subjectRepository.deleteAllById(ids);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean update(SubjectDTO subject) {
        SubjectEntity subjectEntity = subjectRepository.findById(subject.getId()).orElse(null);
        try {
            subjectEntity.setName(subject.getName());
            subjectRepository.save(subjectEntity);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
