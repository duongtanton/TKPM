package com.tkpm.studentsmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.tkpm.studentsmanagement.entity.SubjectEntity;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/

public interface SubjectRepository extends CrudRepository<SubjectEntity, Long> {
    List<SubjectEntity> findAll(Pageable pageable);

    List<SubjectEntity> findAll();

    List<SubjectEntity> findByIdOrNameContaining(Long id, String name, Pageable pageable);
}
