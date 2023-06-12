package com.tkpm.studentsmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.exceptions.ClosedOnExpiredPasswordException;
import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.service.ISubjectService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author : daitt
 * @create : 13/6/2023
 **/
@Controller
@RequestMapping("/subject")
public class SubjectController {
    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ISubjectService subjectService;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest,
                        @RequestParam(required = false, value = "subject") String subjectString) {
        SimpleResponse<SubjectDTO> simpleResponse = new SimpleResponse<>();
        SubjectDTO subjectDTO;
        try {
            subjectDTO = objectMapper.readValue(subjectString, SubjectDTO.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        PageRequest pageable = PageRequest.of(
                simpleRequest.getCurrentPage() - 1,
                simpleRequest.getPerPage(),
                Sort.by("createdDate").descending());
        Integer totalPages = subjectService.totalPages(pageable);

        simpleResponse.setPerPage(pageable.getPageSize());
        simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
        simpleResponse.setTotalPages(totalPages);

        List<SubjectDTO> listSubjects = subjectService.findAll(pageable);
        simpleResponse.setListT(listSubjects);
        model.addAttribute("res", simpleResponse);
        return "subjects/index";
    }

    @PatchMapping
    @ResponseBody
    public Boolean update(SubjectDTO subjectDTO) {
        Boolean updated = false;
        try {
            updated = subjectService.update(subjectDTO);
        }
        catch (Exception e) {
            logger.error(subjectDTO.toString(), e);
        }
        return updated;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SubjectDTO findByID(@PathVariable("id") Long id) {
        SubjectDTO subjectDTO = null;
        try {
            subjectDTO = subjectService.findByID(id);
        }
        catch (Exception e) {
            logger.error(id.toString(), e);
        }
        return subjectDTO;
    }

    @PostMapping
    @ResponseBody
    public Boolean add(SubjectDTO subjectDTO) {
        SubjectDTO newSubjectDTO = null;
        try {
            newSubjectDTO = subjectService.create(subjectDTO);
        }
        catch (Exception e) {
            logger.error(subjectDTO.toString(), e);
        }
        return newSubjectDTO != null;
    }

    @DeleteMapping
    @ResponseBody
    public Boolean delete(DeleteRequest deleteRequest) {
        Boolean deleted = false;
        try {
            deleted = subjectService.delete(deleteRequest.getIds());
        }
        catch (Exception e) {
            logger.error(deleteRequest.getIds().toString(), e);
        }
        return deleted;
    }
}


