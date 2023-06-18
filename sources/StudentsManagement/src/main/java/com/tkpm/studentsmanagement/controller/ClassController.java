package com.tkpm.studentsmanagement.controller;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.ClassStudentDTO;
import com.tkpm.studentsmanagement.dto.DeleteRequest;
import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.service.IClassService;
import com.tkpm.studentsmanagement.service.IStudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/class")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);
    @Autowired
    private IClassService classService;
    @Autowired
    private IStudentService studentService;

    @GetMapping()
    public String showClassList(Model model) {
        classService.autoCreateClass();
       List<ClassDTO> classDTOList = classService.getAll();
       model.addAttribute("listClasses", classDTOList);
       return "classes/class";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ClassDTO showClassById(@PathVariable Long id) {
        ClassDTO classDTO = null;
        try{
            classDTO = classService.findByClassId(id);
        }catch (Exception error) {
            logger.error(id.toString(), error);
        }
        return classDTO;
    }

    @PostMapping("edit")
    @ResponseBody
    public ClassDTO editClass(ClassDTO classDTO) {
        ClassDTO updatedClassDTO = null;
        try {
            updatedClassDTO = classService.save(classDTO);
        } catch (Exception e) {
            logger.error(classDTO.toString(), e);
        }
        return updatedClassDTO;
    }

    @GetMapping("new")
    public String showNewClassForm(Model model) {
        model.addAttribute("class", new ClassDTO());
        model.addAttribute("pageTitle", "Add new class");
        return "classes/class_form";
    }

    @PostMapping("create")
    public String createClass(ClassDTO classDTO) {
        classService.save(classDTO);
        return "redirect:/class";
    }

    @PostMapping("delete")
    @ResponseBody
    public Boolean deleteClass(DeleteRequest deleteRequest) {
        Boolean deleted = false;
        try {
            deleted = classService.delete(deleteRequest.getIds());
        } catch (Exception e) {
            logger.error(deleteRequest.getIds().toString(), e);
        }
        return deleted;
    }

    @PostMapping("import")
    @ResponseBody
    public Boolean importExcel(@RequestParam(value = "file") MultipartFile file,
                               HttpServletResponse httpServletResponse) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        List<ClassDTO> listClassDTO = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Integer rowNum = row.getRowNum();
                if (rowNum != 0) {
                    ClassDTO classDTO = new ClassDTO();
                    try {
                        classDTO.setId((long) row.getCell(0).getNumericCellValue());
                        classDTO.setName(row.getCell(1).getStringCellValue());
                        classDTO.setNumberOfPupils(0);
//                    Date parsedDate;
//                    try {
//                        parsedDate = dateFormatter.parse(row.getCell(2).getStringCellValue());
//                        classDTO.setCreatedDate(new Timestamp(parsedDate.getTime()));
//                    } catch (ParseException e) {
//                        classDTO.setCreatedDate(null);
//                    }
//                    try {
//                        parsedDate = dateFormatter.parse(row.getCell(3).getStringCellValue());
//                        classDTO.setUpdatedDate(new Timestamp(parsedDate.getTime()));
//                    } catch (ParseException e) {
//                        classDTO.setUpdatedDate(null);
//                    }
//                    try {
////                        classDTO.setCreatedBy(studentService.findById((long) row.getCell(4).getNumericCellValue()));
//                    } catch (Exception e) {
//                        classDTO.setCreatedBy(null);
//                    }
//                    try {
////                        // studentDTO.setUpdatedBy((long) row.getCell(5).getNumericCellValue());
//                    } catch (Exception e) {
//                        classDTO.setUpdatedBy(null);
//                    }
                        listClassDTO.add(classDTO);
                    } catch (Exception e) {
                        logger.error(e.toString());
                    }
                }
            }
            workbook.close();
            classService.save(listClassDTO);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(listClassDTO.toString(), e);
            return false;
        }

    }

    @PostMapping("add_student")
    @ResponseBody
    void AddStudent(ClassStudentDTO classStudentDTO) {
        classService.saveStudents(classStudentDTO);
    }
}
