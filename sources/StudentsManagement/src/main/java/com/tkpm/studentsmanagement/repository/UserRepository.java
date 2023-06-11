package com.tkpm.studentsmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tkpm.studentsmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsernameAndEmail(String username, String email);
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String createdBy);
    List<UserEntity> findAll();

    @Query("SELECT u FROM user u " +
            "WHERE (:name IS NULL OR (LOWER(u.name) LIKE CONCAT('%',LOWER(:name),'%'))) " +
            "AND (:createdDate IS NULL OR DATE(u.createdDate) = DATE(:createdDate)) " +
            "AND (:updatedDate IS NULL OR DATE(u.updatedDate) = DATE(:updatedDate)) " +
            "AND (:createdByEmail IS NULL OR u.createdBy.email = :createdByEmail) " +
            "AND (:updatedByEmail IS NULL OR u.updatedBy.email = :updatedByEmail) ")
    Page<UserEntity> findAll(
            @Param("name") String name,
            @Param("createdDate") Timestamp createdDate,
            @Param("updatedDate") Timestamp updatedDate,
            @Param("createdByEmail") String createdByEmail,
            @Param("updatedByEmail") String updatedByEmail,
            Pageable pageable);
}