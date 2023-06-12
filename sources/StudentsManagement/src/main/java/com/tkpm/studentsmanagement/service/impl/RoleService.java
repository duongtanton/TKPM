package com.tkpm.studentsmanagement.service.impl;

import com.tkpm.studentsmanagement.entity.RoleEntity;
import com.tkpm.studentsmanagement.repository.RoleRepository;
import com.tkpm.studentsmanagement.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name);
    }
}