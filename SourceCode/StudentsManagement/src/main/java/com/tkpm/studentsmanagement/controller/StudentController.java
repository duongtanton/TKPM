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
import com.tkpm.studentsmanagement.dto.DeleteRequest;
import com.tkpm.studentsmanagement.dto.SimpleRequest;
import com.tkpm.studentsmanagement.dto.SimpleResponse;
import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.service.IStudentService;
import com.tkpm.studentsmanagement.util.NotNullOrT;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IStudentService studentService;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest,
            @RequestParam(required = false, value = "student") String studentStr) {
        SimpleResponse<StudentDTO> simpleResponse = new SimpleResponse<>();
        StudentDTO studentDTOSearch;
        try {
            studentDTOSearch = objectMapper.readValue(studentStr, StudentDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pageable pageable = PageRequest.of(simpleRequest.getCurrentPage() - 1, simpleRequest.getPerPage(),
                Sort.by("createdDate").descending());
        Integer totalPages = studentService.totalPages(pageable);

        simpleResponse.setPerPage(pageable.getPageSize());
        simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
        simpleResponse.setTotalPages(totalPages);

        List<StudentDTO> listT = studentService.findAll(pageable);
        simpleResponse.setListT(listT);
        model.addAttribute("res", simpleResponse);
        return "students/index";
    }

    @GetMapping("search")
    @ResponseBody
    public SimpleResponse<StudentDTO> search(SimpleRequest simpleRequest,
            @RequestParam(required = false, value = "id") Long id,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "email") String email) {
        SimpleResponse<StudentDTO> simpleResponse = new SimpleResponse<>();

        Pageable pageable = PageRequest.of(simpleRequest.getCurrentPage() - 1, simpleRequest.getPerPage(),
                Sort.by("createdDate").descending());
        Integer totalPages = studentService.totalPages(pageable);

        simpleResponse.setPerPage(pageable.getPageSize());
        simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
        simpleResponse.setTotalPages(totalPages);

        List<StudentDTO> listT = studentService.findLikeByIdOrNameOrEmail(id, name, email, pageable);
        simpleResponse.setListT(listT);
        return simpleResponse;
    }
// SELECT (sb1.exam15Min + sb1.exam45Min + sb1.examMiddle + sb1.examFinal)/4 as HK1, (sb2.exam15Min + sb2.exam45Min + sb2.examMiddle + sb2.examFinal)/4 as HK2 FROM `score_board` as sb1 JOIN `score_board` sb2 ON sb1.studentId = sb2.studentId AND sb1.year = sb2.year AND sb1.subjectId = sb2.subjectId AND sb1.classId = sb2.classId AND sb1.id != sb2.id AND sb1.semester > sb2.semester;
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
    public StudentDTO findById(@PathVariable("id") Long id) {
        StudentDTO studentDTO = null;
        try {
            studentDTO = studentService.findById(id);
        } catch (Exception e) {
            logger.error(id.toString(), e);
        }
        return studentDTO;
    }

    @PostMapping
    @ResponseBody
    public Boolean add(StudentDTO studentDTO) {
        try {
            return studentService.update(studentDTO);
        } catch (Exception e) {
            logger.error(studentDTO.toString(), e);
        }
        return false;
    }

    @DeleteMapping
    @ResponseBody
    public Boolean delete(DeleteRequest deleteRequest) {
        Boolean deleted = false;
        try {
            deleted = studentService.delete(deleteRequest.getIds());
        } catch (Exception e) {
            logger.error(deleteRequest.getIds().toString(), e);
        }
        return deleted;
    }

    @GetMapping("/export")
    public void exportExcel(@RequestParam(value = "ids", required = false) Long[] ids,
            HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Student_" + currentDateTime + ".xlsx";
        httpServletResponse.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Student");
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
        List<StudentDTO> listStudentDTO;
        if (ids != null) {
            listStudentDTO = studentService.findAllById(Arrays.asList(ids));
        } else {
            listStudentDTO = studentService.findAll();
        }
        for (StudentDTO studentDTO : listStudentDTO) {
            Row row = sheet.createRow(rowNum++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(studentDTO.getId());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(studentDTO.getName());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(dateFormatter.format(studentDTO.getCreatedDate()));
            Cell cell4 = row.createCell(3);
            if (studentDTO.getUpdatedDate() != null) {
                cell4.setCellValue(dateFormatter.format(studentDTO.getUpdatedDate()));
            } else {
                cell4.setCellValue("");
            }
            try {
                Cell cell5 = row.createCell(4);
                cell5.setCellValue("" + studentDTO.getCreatedBy().getId());
            } catch (Exception e) {
                // TODO: handle exception
            }
            try {
                Cell cell6 = row.createCell(5);
                cell6.setCellValue("" + studentDTO.getUpdatedBy().getId());
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            logger.error(listStudentDTO.toString().toString(), e);
            e.printStackTrace();
        }
    }

    @GetMapping("/export-example")
    public void expordExcelExample(HttpServletResponse httpServletResponse) {

        httpServletResponse.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Student_" + currentDateTime + ".xlsx";
        httpServletResponse.setHeader(headerKey, headerValue);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Student");
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
                    } catch (Exception e) {
                        studentDTO.setCreatedDate(null);
                    }
                    try {
                        parsedDate = dateFormatter.parse(row.getCell(3).getStringCellValue());
                        studentDTO.setUpdatedDate(new Timestamp(parsedDate.getTime()));
                    } catch (Exception e) {
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
