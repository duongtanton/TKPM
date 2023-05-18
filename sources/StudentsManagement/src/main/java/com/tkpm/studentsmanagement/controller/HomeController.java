package com.tkpm.studentsmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Trả về trang index.html
    }

    @GetMapping("/home")
    public String homepage() {
        return "home"; // Trả về trang home.html
    }
}
