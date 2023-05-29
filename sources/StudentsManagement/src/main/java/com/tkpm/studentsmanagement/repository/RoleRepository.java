package com.tkpm.studentsmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkpm.studentsmanagement.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}