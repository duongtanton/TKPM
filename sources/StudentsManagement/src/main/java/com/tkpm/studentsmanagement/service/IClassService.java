package com.tkpm.studentsmanagement.service;


import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.StudentDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClassService {
    public ClassDTO findByClassId(Long classId);
    public ClassDTO findByClassName(String className);
    public List<ClassDTO> getAll();
//    public List<ClassDTO> getAll(Pageable pageable);
public ClassDTO save(ClassDTO userDTO);
    public List<ClassDTO> save(List<ClassDTO> userDTO);
    public Boolean delete(List<Long> id);
}
