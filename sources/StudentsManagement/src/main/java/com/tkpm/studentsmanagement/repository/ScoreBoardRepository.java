package com.tkpm.studentsmanagement.repository;

import com.tkpm.studentsmanagement.dto.ScoreBoardDTO;
import com.tkpm.studentsmanagement.entity.ScoreBoardEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author : daitt
 * @create : 5/6/2023
 **/

public interface ScoreBoardRepository extends CrudRepository<ScoreBoardEntity, Long>{
    List<ScoreBoardEntity> findAll(Pageable pageable);
    List<ScoreBoardEntity> findAll();
}
