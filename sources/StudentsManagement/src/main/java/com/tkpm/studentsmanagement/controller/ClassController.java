package com.tkpm.studentsmanagement.controller;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.service.IClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/class")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private IClassService classService;

    @GetMapping()
    public String showClassList(Model model) {
       List<ClassDTO> classDTOList = classService.getAll();
       model.addAttribute("listClasses", classDTOList);
       return "classes/class";
    }

//    @PostMapping("edit/:id")
//    public String editClass(Long id) {
//        System.out.println("-------------/////////////---------------");
//        System.out.println(id + 123456);
//        return "classes/class";
//    }

    @GetMapping("new")
    public String showNewClassForm(Model model) {
        model.addAttribute("class", new ClassDTO());
        return "classes/class_form";
    }

    @PostMapping("create")
    public String createClass(ClassDTO classDTO) {
        classService.save(classDTO);
        return "redirect:/class";
    }
}
