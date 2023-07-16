package com.tkpm.studentsmanagement.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.ClassStudentDTO;
import com.tkpm.studentsmanagement.dto.DeleteRequest;
import com.tkpm.studentsmanagement.dto.SimpleRequest;
import com.tkpm.studentsmanagement.dto.SimpleResponse;
import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.service.IClassService;
import com.tkpm.studentsmanagement.service.IClassStudentService;
import com.tkpm.studentsmanagement.service.IStudentService;
import com.tkpm.studentsmanagement.util.NotNullOrT;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/class-student")
public class ClassStudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IClassService classService;

    @Autowired
    private IClassStudentService classStudentService;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest,
            @RequestParam(required = false, value = "classId") Long classId) {
        SimpleResponse<ClassStudentDTO> simpleResponse = new SimpleResponse<>();

        Pageable pageable = PageRequest.of(simpleRequest.getCurrentPage() - 1, simpleRequest.getPerPage(),
                Sort.by("createdDate").descending());
        Integer totalPages = studentService.totalPages(pageable);

        simpleResponse.setPerPage(pageable.getPageSize());
        simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
        simpleResponse.setTotalPages(totalPages);
        List<ClassDTO> listClassDTO = classService.getAll();

        if (listClassDTO.size() > 0 && classId == null) {
            classId = listClassDTO.get(0).getId();
        } else if (classId == null) {
            classId = 0L;
        }

        List<ClassStudentDTO> listT = classStudentService.findByClasssId(classId, pageable);
        // simpleResponse.setListT(listT);
        model.addAttribute("res", simpleResponse);
        model.addAttribute("classes", listClassDTO);
        model.addAttribute("classId", classId);
        model.addAttribute("classs", classService.findById(classId));
        model.addAttribute("classStudents", listT);

        return "classstudent/index";
    }

    @PatchMapping
    @ResponseBody
    public Boolean update(StudentDTO student) {
        Boolean updated = false;
        try {
            updated = studentService.update(student);
        } catch (Exception e) {
            logger.error(student.toString(), e);
        }
        return updated;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ClassStudentDTO findById(@PathVariable("id") Long id) {
        ClassStudentDTO classStudentDTO = null;
        try {
            classStudentDTO = classStudentService.findById(id);
        } catch (Exception e) {
            logger.error(id.toString(), e);
        }
        return classStudentDTO;
    }

    @PostMapping
    @ResponseBody
    public Boolean add(ClassStudentDTO classStudent) {
        try {
            return classStudentService.create(classStudent) != null;
        } catch (Exception e) {
            logger.error(classStudent.toString(), e);
        }
        return false;
    }

    @DeleteMapping
    @ResponseBody
    public Boolean delete(DeleteRequest deleteRequest) {
        Boolean deleted = false;
        try {
            deleted = classStudentService.delete(deleteRequest.getIds());
        } catch (Exception e) {
            logger.error(deleteRequest.getIds().toString(), e);
        }
        return deleted;
    }

    @GetMapping("/export/{classId}")
    public void exportExcel(@PathVariable("classId") Long classId,
            HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Classes_" + currentDateTime + ".xlsx";
        httpServletResponse.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Student in class");
        ClassDTO classDTO = classService.findById(classId);
        Row _row = sheet.createRow(0);
        Cell _cell1 = _row.createCell(0);
        _cell1.setCellValue("Year");
        Cell _cell2 = _row.createCell(1);
        _cell2.setCellValue(classDTO.getYear() + "-" + classDTO.getYear() + 1);
        Cell _cell3 = _row.createCell(2);
        _cell3.setCellValue("Numbers");
        Cell _cell4 = _row.createCell(3);
        
        Cell _cell5 = _row.createCell(4);
        _cell5.setCellValue("Name");
        Cell _cell6 = _row.createCell(5);
        _cell6.setCellValue(classDTO.getName());

        int rowNum = 1;
        Row __row = sheet.createRow(rowNum++);
        Cell __cell1 = __row.createCell(0);
        __cell1.setCellValue("Stt");
        Cell __cell2 = __row.createCell(1);
        __cell2.setCellValue("Fullname");
        Cell __cell3 = __row.createCell(2);
        __cell3.setCellValue("Gender");
        Cell __cell4 = __row.createCell(3);
        __cell4.setCellValue("Birthday");
        Cell __cell5 = __row.createCell(4);
        __cell5.setCellValue("Adresss");
        List<ClassStudentDTO> listStudentDTO = classStudentService.findByClasssId(classId);
        _cell4.setCellValue(listStudentDTO.size());
        for (Integer i = 0; listStudentDTO != null && i < listStudentDTO.size(); i++) {
            StudentDTO studentDTO = listStudentDTO.get(i).getStudent();
            Row row = sheet.createRow(rowNum++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(i + 1);
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(studentDTO.getName());
            Cell cell3 = row.createCell(2);
            if (studentDTO.getSex().equals(1)) {
                cell3.setCellValue("Male");
            } else {
                cell3.setCellValue("Femail");
            }
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(studentDTO.getAddress());
        }

        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/export-example")
    public void expordExcelExample(HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Classes_" + currentDateTime + ".xlsx";
        httpServletResponse.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Classes");
        int rowNum = 0;
        Row _row = sheet.createRow(rowNum++);
        Cell _cell1 = _row.createCell(0);
        _cell1.setCellValue("id");
        Cell _cell2 = _row.createCell(1);
        _cell2.setCellValue("name");
        Cell _cell3 = _row.createCell(2);
        _cell3.setCellValue("createdDate");
        Cell _cell4 = _row.createCell(3);
        _cell4.setCellValue("updatedDate");
        Cell _cell5 = _row.createCell(4);
        _cell5.setCellValue("createdBy");
        Cell _cell6 = _row.createCell(5);
        _cell6.setCellValue("updatedBy");

        Row row = sheet.createRow(rowNum++);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(1);
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Nguyen Van A");
        Cell cell3 = row.createCell(2);
        cell3.setCellValue(dateFormatter.format(new Date()));
        Cell cell4 = row.createCell(3);
        cell4.setCellValue(dateFormatter.format(new Date()));
        Cell cell5 = row.createCell(4);
        cell5.setCellValue(1);
        Cell cell6 = row.createCell(5);
        cell6.setCellValue(1);

        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    @PostMapping(value = "/import")
    @ResponseBody
    public Boolean importExcel(@RequestParam(value = "file") MultipartFile file,
            HttpServletResponse httpServletResponse) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        List<StudentDTO> listStudentDTO = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Integer rowNum = row.getRowNum();
                if (rowNum == 0) {
                    continue;
                } else {
                    StudentDTO studentDTO = new StudentDTO();
                    try {
                        studentDTO.setId((long) row.getCell(0).getNumericCellValue());
                    } catch (Exception e) {
                        studentDTO.setId(null);
                    }
                    studentDTO.setName(row.getCell(1).getStringCellValue());
                    Date parsedDate;
                    try {
                        parsedDate = dateFormatter.parse(row.getCell(2).getStringCellValue());
                        studentDTO.setCreatedDate(new Timestamp(parsedDate.getTime()));
                    } catch (ParseException e) {
                        studentDTO.setCreatedDate(null);
                    }
                    try {
                        parsedDate = dateFormatter.parse(row.getCell(3).getStringCellValue());
                        studentDTO.setUpdatedDate(new Timestamp(parsedDate.getTime()));
                    } catch (ParseException e) {
                        studentDTO.setUpdatedDate(null);
                    }
                    try {
                        // studentDTO.setCreatedBy((long) row.getCell(4).getNumericCellValue());
                    } catch (Exception e) {
                        studentDTO.setCreatedBy(null);
                    }
                    try {
                        // studentDTO.setUpdatedBy((long) row.getCell(5).getNumericCellValue());
                    } catch (Exception e) {
                        studentDTO.setUpdatedBy(null);
                    }
                    listStudentDTO.add(studentDTO);
                }
            }
            workbook.close();
            studentService.create(listStudentDTO);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(listStudentDTO.toString(), e);
            return false;
        }

    }
}
