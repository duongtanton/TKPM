package com.tkpm.studentsmanagement.repository;

import com.tkpm.studentsmanagement.constant.ReportType;
import com.tkpm.studentsmanagement.entity.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
    @Query("SELECT r FROM report r " +
            "WHERE (:classId IS NULL OR r.classs.id = :classId) " +
            "AND (:subjectId IS NULL OR r.subject.id = :subjectId) " +
            "AND (:type IS NULL OR r.type = :type) " +
            "AND (:year IS NULL OR r.year = :year) " +
            "AND (:semester IS NULL OR r.semester = :semester) ")
    ReportEntity findByStatistic(
            @Param("classId") Long classId,
            @Param("subjectId") Long subjectId,
            @Param("type") ReportType type,
            @Param("year") Integer year,
            @Param("semester") Integer semester);

    @Query("SELECT r FROM report r " +
            "WHERE (:classId IS NULL OR r.classs.id = :classId) " +
            "AND (:subjectId IS NULL OR r.subject.id = :subjectId) " +
            "AND (:type IS NULL OR r.type = :type) " +
            "AND (:year IS NULL OR r.year = :year) " +
            "AND (:semester IS NULL OR r.semester = :semester) ")
    Page<ReportEntity> findAll(
            @Param("classId") Long classId,
            @Param("subjectId") Long subjectId,
            @Param("type") ReportType type,
            @Param("year") Integer year,
            @Param("semester") Integer semester,
            Pageable pageable);
}
