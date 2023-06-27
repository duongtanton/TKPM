package com.tkpm.studentsmanagement.service;

import com.tkpm.studentsmanagement.dto.SubjectDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/
public interface ISubjectService {
    SubjectDTO create(SubjectDTO subject);

    List<SubjectDTO> create(List<SubjectDTO> listSubject);

    List<SubjectDTO> findAll(Pageable pageable);


    List<SubjectDTO> findAll();

    SubjectDTO findByID(Long id);

    Integer totalPages(Pageable pageable);

    Boolean delete(List<Long> ids);

    Boolean update(SubjectDTO subject);

    List<SubjectDTO> findLikeByIdOrName(Long id, String name, Pageable pageable);
}
