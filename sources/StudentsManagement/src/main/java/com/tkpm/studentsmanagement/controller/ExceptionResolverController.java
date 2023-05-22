package com.tkpm.studentsmanagement.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionResolverController implements ErrorController {

    @GetMapping("/error")
    public String handle404Error() {
        return "error404"; // Assuming you have an error404.html template
    }

    public String getErrorPath() {
        return "/error";
    }
}