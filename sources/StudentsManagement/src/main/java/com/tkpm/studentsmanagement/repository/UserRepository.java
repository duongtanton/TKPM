package com.tkpm.studentsmanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.tkpm.studentsmanagement.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    public UserEntity findByUsernameAndEmail(String username, String email);
    public UserEntity findByUsername(String username);
}
