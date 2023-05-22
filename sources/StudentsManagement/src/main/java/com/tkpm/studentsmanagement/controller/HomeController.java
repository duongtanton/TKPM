package com.tkpm.studentsmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkpm.studentsmanagement.dto.StudentDTO;
import com.tkpm.studentsmanagement.service.IStudentService;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IStudentService studentService;

    @GetMapping("/")
    @ResponseBody
    public StudentDTO index() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Duong Tan Ton");
        logger.info("Hello");
        return studentService.create(studentDTO); // Trả về trang index.html
    }

    @GetMapping("/alternative")
    public String alternative() {
        return "alternative"; // Trả về trang home.html
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Trả về trang home.html
    }
}
