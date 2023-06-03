package com.tkpm.studentsmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/class")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping()
    public String getHello() {
        return "classes/class";
    }
}
