package com.tkpm.studentsmanagement.service;

import com.tkpm.studentsmanagement.entity.RoleEntity;

public interface IRoleService {
    RoleEntity findByName(String name);
}