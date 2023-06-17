package com.tkpm.studentsmanagement.service.impl;

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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassService implements IClassService {
    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);
    @Autowired private ClassRepository classRepository;
    @Autowired private ClassStudentRepository classStudentRepository;
    @Autowired private StudentService studentService;
    @Autowired private ModelMapper modelMapper;
    final Integer MAX_STUDENTS_IN_CLASS = 40;
    @Override
    public void autoCreateClass() {
        if(classRepository.count() == 0) {
            try {
                //Get default class from file 'default_classes.xlsx'
                List<ClassDTO> listClassDTO = new ArrayList<>();
                XSSFWorkbook workbook = new XSSFWorkbook("src/default_classes.xlsx");
                XSSFSheet sheet = workbook.getSheetAt(0);
                logger.info(sheet.toString());
                for (Row row : sheet) {
                    Integer rowNum = row.getRowNum();
                    if (rowNum != 0) {
                        ClassDTO classDTO = new ClassDTO();
                        classDTO.setId((long) row.getCell(0).getNumericCellValue());
                        classDTO.setName(row.getCell(1).getStringCellValue());
                        classDTO.setSchoolYear(row.getCell(2).getStringCellValue());
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
    public ClassDTO findByClassId(Long classId) {
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
        if(classEntityList.size()>0) return modelMapper.map(classEntityList.get(0), ClassDTO.class);
        return new ClassDTO();
    }

    @Override
    public List<ClassDTO> getAll() {
        List<ClassDTO> result = new ArrayList<>();
        Iterable<ClassEntity> listClasses = classRepository.findAll();

        classRepository.findAll().forEach(classEntity -> {
            result.add(modelMapper.map(classEntity, ClassDTO.class) );
        });
        return result;
    }



//    @Override
//    public List<ClassDTO> getAll(Pageable pageable) {
//        List<StudentEntity> listStudentEntity = classRepository.findAll(pageable);
//        return modelMapper.map(listStudentEntity, new TypeToken<List<StudentDTO>>() {
//        }.getType());
//    }

    @Override
    public ClassDTO save(ClassDTO classDTO) {
        ClassEntity newClass = new ClassEntity();
        if(classDTO.getId() != null) {
            newClass.setId(classDTO.getId());
        }
        newClass.setName(classDTO.getName());
        if(classDTO.getNumberOfPupils() != null) {
            newClass.setNumberOfPupils(classDTO.getNumberOfPupils());
        }
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
    public boolean saveStudents(ClassStudentDTO classStudentDTO) {
        try {
            ClassDTO classDTO = this.findByClassName(classStudentDTO.getClassName());
            logger.info(classDTO.getName() + " " + classDTO.getNumberOfPupils());
            logger.info(classDTO.getName() + " " + classDTO.getCreatedDate());
            if (classDTO.getId() != null) {
                //Create new student
                StudentDTO newStudent = new StudentDTO();
                newStudent.setName(classStudentDTO.getStudentName());
                newStudent.setSex(classStudentDTO.getSex());
                newStudent.setAddress(classStudentDTO.getAddress());
                newStudent.setBirthDate(classStudentDTO.getBirthDate());
                StudentDTO savedStudent = studentService.create(newStudent);

                //Add student to the class
                ClassStudentEntity classStudentEntity = new ClassStudentEntity();
                classStudentEntity.setClassEntity(modelMapper.map(classDTO, ClassEntity.class));
                classStudentEntity.setStudentEntity(modelMapper.map(savedStudent, StudentEntity.class));
                classStudentEntity.setStatus(Boolean.TRUE);
                this.classStudentRepository.save(classStudentEntity);

                //Update number of student in class
                classDTO.setNumberOfPupils(classDTO.getNumberOfPupils() + 1);
                logger.info(classDTO.getNumberOfPupils().toString());
                this.classRepository.save(modelMapper.map(classDTO, ClassEntity.class));
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return true;
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
}
