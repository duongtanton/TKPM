package com.tkpm.studentsmanagement.controller;

import com.tkpm.studentsmanagement.dto.ClassDTO;
import com.tkpm.studentsmanagement.dto.DeleteRequest;
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

    @GetMapping("/{id}")
    @ResponseBody
    public ClassDTO showClassById(@PathVariable Long id) {
        ClassDTO classDTO = null;
        try{
            classDTO = classService.findByClassId(id);
        }catch (Exception error) {
            logger.error(id.toString(), error);
        }
        return classDTO;
    }

    @PostMapping("edit")
    @ResponseBody
    public ClassDTO editClass(ClassDTO classDTO) {
        ClassDTO updatedClassDTO = null;
        try {
            updatedClassDTO = classService.save(classDTO);

        } catch (Exception e) {
            logger.error(classDTO.toString(), e);
        }
        return updatedClassDTO;
    }

    @GetMapping("new")
    public String showNewClassForm(Model model) {
        model.addAttribute("class", new ClassDTO());
        model.addAttribute("pageTitle", "Add new class");
        return "classes/class_form";
    }

    @PostMapping("create")
    public String createClass(ClassDTO classDTO) {
        logger.info(classDTO.getName());
        classService.save(classDTO);
        return "redirect:/class";
    }

    @PostMapping("delete")
    @ResponseBody
    public Boolean deleteClass(DeleteRequest deleteRequest) {
        Boolean deleted = false;
        try {
            deleted = classService.delete(deleteRequest.getIds());
        } catch (Exception e) {
            logger.error(deleteRequest.getIds().toString(), e);
        }
        return deleted;
    }
}
