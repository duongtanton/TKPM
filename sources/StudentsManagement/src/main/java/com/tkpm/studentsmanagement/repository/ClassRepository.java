package com.tkpm.studentsmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tkpm.studentsmanagement.entity.ClassEntity;

public interface ClassRepository extends CrudRepository<ClassEntity, Long> {
    // @Query("SELECT * FROM class" +
    // "WHERE (:name IS NULL OR (LOWER(class.name) LIKE
    // CONCAT('%',LOWER(:name),'%'))) " +
    // "AND (:createdDate IS NULL OR DATE(class.createdDate) = DATE(:createdDate)) "
    // +
    // "AND (:updatedDate IS NULL OR DATE(class.updatedDate) = DATE(:updatedDate)) "
    // +
    // "AND (:createdByEmail IS NULL OR class.createdBy.email = :createdByEmail) " +
    // "AND (:updatedByEmail IS NULL OR class.updatedBy.email = :updatedByEmail) ")
    // Page<ClassEntity> findAll(
    // @Param("name") String name,
    // @Param("createdDate") Timestamp createdDate,
    // @Param("updatedDate") Timestamp updatedDate,
    // @Param("createdByEmail") String createdByEmail,
    // @Param("updatedByEmail") String updatedByEmail,
    // Pageable pageable);
    @Query("SELECT c FROM class c WHERE c.name = :name AND c.year = :year")
    List<ClassEntity> findByNameAndSchoolYear(@Param("name") String name, @Param("year") String year);

    List<ClassEntity> findByName(@Param("name") String name);

    List<ClassEntity> findByIdOrNameContainingOrYear(Long id, String name, String year, Pageable pageable);
}
