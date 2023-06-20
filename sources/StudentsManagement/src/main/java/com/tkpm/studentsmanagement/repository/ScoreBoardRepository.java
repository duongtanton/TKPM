package com.tkpm.studentsmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tkpm.studentsmanagement.entity.ScoreBoardEntity;

/**
 * @author : daitt
 * @create : 5/6/2023
 **/

public interface ScoreBoardRepository extends CrudRepository<ScoreBoardEntity, Long> {
    List<ScoreBoardEntity> findAll(Pageable pageable);

    List<ScoreBoardEntity> findAll();

    @Query("SELECT sb1.student, sb1.classs, " +
            "AVG((sb1.exam15Min*1 + sb1.exam45Min*2 + sb1.examMiddle*3 + sb1.examFinal*4)/10) as HK1, " +
            "AVG((sb2.exam15Min*1 + sb2.exam45Min*2 + sb2.examMiddle*3 + sb2.examFinal*4)/10) as HK2 " +
            "FROM score_board as sb1 INNER JOIN score_board as sb2 " +
            "ON sb1.student = sb2.student AND sb1.year = sb2.year AND sb1.classs = sb2.classs " +
            "AND sb1.id != sb2.id AND sb1.semester > sb2.semester AND sb1.classs.id=:classsId GROUP BY sb1.student, sb1.classs")
    List<Object[]> staticAverageByClass(Long classsId);
}
