package com.tkpm.studentsmanagement.service;

import com.tkpm.studentsmanagement.dto.SubjectDTO;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/
public interface ISubjectService {
    SubjectDTO create(SubjectDTO subject);
    List<SubjectDTO> create(List<SubjectDTO> listSubject);
    List<SubjectDTO> findAll(Pageable pageable);

    List<SubjectDTO> findAll(org.springframework.data.domain.Pageable pageable);

    List<SubjectDTO> findAll();
    SubjectDTO findByID(Long subjectID);
    Integer totalPages(Pageable pageable);

    Integer totalPages(org.springframework.data.domain.Pageable pageable);

    Boolean delete(List<Long> ids);
    Boolean update(SubjectDTO subject);
}
