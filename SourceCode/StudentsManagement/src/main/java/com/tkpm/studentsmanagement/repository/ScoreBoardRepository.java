package com.tkpm.studentsmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tkpm.studentsmanagement.entity.ScoreBoardEntity;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT sb FROM score_board sb " +
            "WHERE (:classs IS NULL OR sb.classs.id = :classs) " +
            "AND (:student IS NULL OR sb.student.id = :student) " +
            "AND (:subject IS NULL OR sb.subject.id = :subject )" +
            "AND (:year IS NULL OR sb.year = :year) " +
            "AND (:semester IS NULL OR sb.semester = :semester) ")
    Page<ScoreBoardEntity> findAll(
            @Param("classs") Long classs,
            @Param("student") Long student,
            @Param("subject") Long subject,
            @Param("year") Integer year,
            @Param("semester") Integer semester,
            Pageable pageable);

    @Query("SELECT sb.classs.id, sb.subject.id, sb.year, sb.semester, AVG(sb.averageScore), " +
            "SUM(CASE WHEN sb.averageScore >= :requiredMark THEN 1 ELSE 0 END) AS passQuantity " +
            "FROM score_board sb " +
            "GROUP BY sb.classs.id, sb.subject.id, sb.year, sb.semester ")
    List<Object[]> statisticBySubject(
            @Param("requiredMark") Double requiredMark);

    @Query("SELECT s.classId, s.year, s.semester, SUM(CAST(s.passQuantity AS integer)) AS passQuantity, AVG(s.average) AS average " +
            "FROM (" +
            "SELECT sb.classs.id AS classId, sb.student.id AS studentId, sb.year AS year, sb.semester AS semester, " +
            "AVG(sb.averageScore) AS average, " +
            "(CASE WHEN SUM(CASE WHEN sb.averageScore >= :requiredMark THEN 1 ELSE 0 END) = (SELECT COUNT(sj) FROM subject sj) THEN 1 ELSE 0 END) AS passQuantity " +
            "FROM score_board sb " +
            "GROUP BY sb.classs.id, sb.student.id, sb.year, sb.semester " +
            ") AS s " +
            "GROUP BY s.classId, s.year, s.semester ")
    List<Object[]> statisticByStudent(
            @Param("requiredMark") Double requiredMark
    );
}
