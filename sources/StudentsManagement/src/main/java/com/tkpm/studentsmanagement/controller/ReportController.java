package com.tkpm.studentsmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.dto.SimpleRequest;
import com.tkpm.studentsmanagement.dto.SimpleResponse;
import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.service.IStudentService;

@Controller
@RequestMapping("/report")
public class ReportController {
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
        return "reports/index";
    }
}
