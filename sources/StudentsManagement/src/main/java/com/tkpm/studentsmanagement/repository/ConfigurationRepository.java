package com.tkpm.studentsmanagement.repository;

import com.tkpm.studentsmanagement.entity.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    List<Configuration> findAll();

    Configuration findByName(String name);

    @Query("SELECT c FROM configuration c " +
            "WHERE (:text IS NULL OR ((LOWER(c.name) LIKE CONCAT('%',LOWER(:text),'%')) " +
            "OR (LOWER(c.description) LIKE CONCAT('%',LOWER(:text),'%')))) " +
            "AND (:createdDate IS NULL OR DATE(c.createdDate) = DATE(:createdDate)) " +
            "AND (:updatedDate IS NULL OR DATE(c.updatedDate) = DATE(:updatedDate)) " +
            "AND (:createdByEmail IS NULL OR c.createdBy.email = :createdByEmail) " +
            "AND (:updatedByEmail IS NULL OR c.updatedBy.email = :updatedByEmail) ")
    Page<Configuration> findAll(
            @Param("text") String text,
            @Param("createdDate") Timestamp createdDate,
            @Param("updatedDate") Timestamp updatedDate,
            @Param("createdByEmail") String createdByEmail,
            @Param("updatedByEmail") String updatedByEmail,
            Pageable pageable);
}
