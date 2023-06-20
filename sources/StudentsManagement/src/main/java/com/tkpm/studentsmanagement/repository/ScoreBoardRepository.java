package com.tkpm.studentsmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.tkpm.studentsmanagement.entity.ScoreBoardEntity;

/**
 * @author : daitt
 * @create : 5/6/2023
 **/

public interface ScoreBoardRepository extends CrudRepository<ScoreBoardEntity, Long>{
    List<ScoreBoardEntity> findAll(Pageable pageable);
    List<ScoreBoardEntity> findAll();
}
