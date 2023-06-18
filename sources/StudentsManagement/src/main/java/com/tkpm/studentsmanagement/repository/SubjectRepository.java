package com.tkpm.studentsmanagement.repository;

import com.tkpm.studentsmanagement.entity.SubjectEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/

public interface SubjectRepository extends CrudRepository<SubjectEntity, Long> {
    List<SubjectEntity> findAll(Pageable pageable);
    List<SubjectEntity> findAll();
}
