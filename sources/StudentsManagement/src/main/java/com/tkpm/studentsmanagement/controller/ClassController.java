package com.tkpm.studentsmanagement.controller;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.service.IClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/class")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);
    @Autowired
    private IClassService classService;

    @GetMapping()
    public String showClassList(Model model) {
       List<ClassDTO> classDTOList = classService.getAll();
       model.addAttribute("listClasses", classDTOList);
       return "classes/class";
    }

    @GetMapping("edit/{id}")
    public String editClass(@PathVariable Long id, Model model, RedirectAttributes ra) {
        ClassDTO classDTO = classService.findByClassId(id);
        model.addAttribute("class", classDTO);
        model.addAttribute("pageTitle", "Edit user (ID: " + id + ")");
        return "classes/class_form";
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

    @GetMapping("delete/{id}")
    public String deleteClass(@PathVariable Long id) {
        logger.info("Delete " + id);
        classService.delete(id);
        return "redirect:/class";
    }
}
