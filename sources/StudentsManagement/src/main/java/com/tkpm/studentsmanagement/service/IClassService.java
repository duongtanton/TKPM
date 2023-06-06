package com.tkpm.studentsmanagement.service;


import com.tkpm.studentsmanagement.dto.ClassDTO;

import java.util.List;

public interface IClassService {
    public ClassDTO findByClassId(Long classId);
    public ClassDTO findByClassName(String className);
    public List<ClassDTO> getAll();

    public ClassDTO save(ClassDTO userDTO);
    public Boolean delete(Long id);
}
