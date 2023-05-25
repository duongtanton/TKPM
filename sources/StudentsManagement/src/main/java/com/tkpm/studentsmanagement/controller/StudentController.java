package com.tkpm.studentsmanagement.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
    private IStudentService studentService;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest) {
        SimpleResponse<StudentDTO> simpleResponse = new SimpleResponse<StudentDTO>();

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

    @PatchMapping
    @ResponseBody
    public Boolean update(StudentDTO student) {
        return studentService.update(student);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public StudentDTO findById(@PathVariable("id") Long id) {
        return studentService.findById(id);
    }

    @PostMapping
    @ResponseBody
    public Boolean add(StudentDTO studentDTO) {
        StudentDTO newStudentDTO = studentService.create(studentDTO);
        return newStudentDTO != null;
    }

    @DeleteMapping
    @ResponseBody
    public Boolean delete(DeleteRequest deleteRequest) {
        Boolean deleted = studentService.delete(deleteRequest.getIds());
        return deleted;
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse httpServletResponse) {
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

        List<StudentDTO> listStudentDTO = studentService.findAll();
        for (StudentDTO studentDTO : listStudentDTO) {
            Row row = sheet.createRow(rowNum++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(studentDTO.getId());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(studentDTO.getName());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(dateFormatter.format(studentDTO.getCreatedDate()));
            Cell cell4 = row.createCell(3);
            if (studentDTO.getUpdatedBy() != null) {
                cell4.setCellValue(dateFormatter.format(studentDTO.getUpdatedBy()));
            } else {
                cell4.setCellValue("");
            }
            Cell cell5 = row.createCell(4);
            cell5.setCellValue("" + studentDTO.getCreatedBy());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue("" + NotNullOrT.run(studentDTO.getUpdatedBy(), ""));
        }

        try {
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
