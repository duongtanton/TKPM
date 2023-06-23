package com.tkpm.studentsmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.ClassStudentDTO;
import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.entity.ClassStudentEntity;
import com.tkpm.studentsmanagement.entity.StudentEntity;
import com.tkpm.studentsmanagement.repository.ClassRepository;
import com.tkpm.studentsmanagement.repository.ClassStudentRepository;
import com.tkpm.studentsmanagement.service.IClassService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClassService implements IClassService {
    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private ClassStudentRepository classStudentRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ModelMapper modelMapper;
    final Integer MAX_STUDENTS_IN_CLASS = 40;

    @Override
    public void autoCreateClass() {
        if (classRepository.count() == 0) {
            try {
                // Get default class from file 'default_classes.xlsx'
                List<ClassDTO> listClassDTO = new ArrayList<>();
                XSSFWorkbook workbook = new XSSFWorkbook("src/default_classes.xlsx");
                XSSFSheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    Integer rowNum = row.getRowNum();
                    if (rowNum != 0) {
                        ClassDTO classDTO = new ClassDTO();
                        classDTO.setId((long) row.getCell(0).getNumericCellValue());
                        classDTO.setName(row.getCell(1).getStringCellValue());
                        classDTO.setYear(row.getCell(2).getStringCellValue());
                        listClassDTO.add(classDTO);
                    }
                }
                workbook.close();
                this.save(listClassDTO);
            } catch (Exception error) {
                logger.error(error.toString());
            }
        }
    }

    @Override
    public ClassDTO findByClasssId(Long classId) {
        Optional<ClassEntity> optionalClassEntity = classRepository.findById(classId);
        ClassDTO classDTO = null;

        try {
            classDTO = modelMapper.map(optionalClassEntity, ClassDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classDTO;
    }

    @Override
    public ClassDTO findByClassName(String className) {
        List<ClassEntity> classEntityList = classRepository.findByName(className);
        if (classEntityList.size() > 0)
            return modelMapper.map(classEntityList.get(0), ClassDTO.class);
        return new ClassDTO();
    }

    @Override
    public List<ClassDTO> getAll() {
        List<ClassDTO> result = new ArrayList<>();
        Iterable<ClassEntity> listClasses = classRepository.findAll();

        classRepository.findAll().forEach(classEntity -> {
            result.add(modelMapper.map(classEntity, ClassDTO.class));
        });
        return result;
    }

    // @Override
    // public List<ClassDTO> getAll(Pageable pageable) {
    // List<StudentEntity> listStudentEntity = classRepository.findAll(pageable);
    // return modelMapper.map(listStudentEntity, new TypeToken<List<StudentDTO>>() {
    // }.getType());
    // }

    @Override
    public ClassDTO save(ClassDTO classDTO) {
        ClassEntity newClass = modelMapper.map(classDTO, ClassEntity.class);
        return modelMapper.map(classRepository.save(newClass), ClassDTO.class);
    }

    @Override
    public List<ClassDTO> save(List<ClassDTO> listClass) {
        List<ClassEntity> listStudentEntity = modelMapper.map(listClass, new TypeToken<List<ClassEntity>>() {
        }.getType());
        List<ClassDTO> listClassDTO = modelMapper.map(classRepository.saveAll(listStudentEntity),
                new TypeToken<List<StudentDTO>>() {
                }.getType());
        return listClassDTO;
    }

    @Override
    public ClassStudentDTO saveStudents(ClassStudentDTO classStudentDTO) {
        try {
            if (classStudentDTO.getStudent() == null || classStudentDTO.getClasss() == null)
                return null;

            Long classId = classStudentDTO.getClasss().getId();
            Long studentId = classStudentDTO.getStudent().getId();
            // Get class
            ClassDTO classDTO = this.findByClasssId(classId);
            if (classDTO.getNumberOfPupils() == 40)
                return null;

            // Get student
            StudentDTO studentDTO = studentService.findById(studentId);

            // Map
            ClassEntity classEntity = modelMapper.map(classDTO, ClassEntity.class);
            StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);

            // Check if student already in class
            List<ClassStudentEntity> classEntityList = classStudentRepository.findStudentInClass(classEntity,
                    studentEntity);
            if (classEntityList.size() > 0)
                return null;

            // Add student to the class
            ClassStudentEntity classStudentEntity = new ClassStudentEntity();
            classStudentEntity.setClasss(classEntity);
            classStudentEntity.setStudent(studentEntity);
            classStudentEntity.setStatus(Boolean.TRUE);
            this.classStudentRepository.save(classStudentEntity);

            // Update number of student in class
            classDTO.setNumberOfPupils(classDTO.getNumberOfPupils() + 1);
            return modelMapper.map(this.classRepository.save(modelMapper.map(classDTO, ClassEntity.class)),
                    ClassStudentDTO.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    @Override
    public Boolean delete(List<Long> listClassIds) {
        try {
            classRepository.deleteAllById(listClassIds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ClassDTO> findLikeByIdOrNameOrYear(Long id, String name, String year, Pageable pageable) {
        List<ClassEntity> studentEntities = classRepository.findByIdOrNameContainingOrYear(id, name, year, pageable);
        return modelMapper.map(studentEntities, new TypeToken<List<StudentDTO>>() {
        }.getType());
    }

    @Override
    public List<String> getAllYear() {
        return classRepository.findAllYear();
    }

    @Override
    public boolean update(ClassDTO classDTO) {
        ClassEntity classEntity = classRepository.findById(classDTO.getId()).orElse(null);
        try {
            classEntity.setName(classDTO.getName());
            classEntity.setYear(classDTO.getYear());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    @Override
    public ClassDTO findById(Long id) {
        ClassEntity classDTO = classRepository.findById(id).orElse(null);

        return modelMapper.map(classDTO, ClassDTO.class);
    }
}
