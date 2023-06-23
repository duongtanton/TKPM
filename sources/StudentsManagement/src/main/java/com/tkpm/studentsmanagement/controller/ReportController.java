package com.tkpm.studentsmanagement.controller;

import java.util.List;

import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.service.impl.ClassService;
import com.tkpm.studentsmanagement.service.impl.ReportService;
import com.tkpm.studentsmanagement.service.impl.SubjectService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/report")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ClassService classService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest,
                        @RequestParam(required = false, value = "report") String reportStr) {
        Pageable pageable = PageRequest.of(simpleRequest.getCurrentPage() - 1, simpleRequest.getPerPage(),
                Sort.by("createdDate").descending());
        ReportDTO reportDTO = new ReportDTO();
        System.out.println(reportStr);
        if (reportStr != null && !reportStr.isEmpty()) {
            try {
                reportDTO = objectMapper.readValue(reportStr, ReportDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SimpleResponse<?> simpleResponse = reportService.findAll(reportDTO, pageable);
        model.addAttribute("res", simpleResponse);
        model.addAttribute("year", classService.getAllYear());
        model.addAttribute("defaultYear", classService.getAllYear().get(0));
        model.addAttribute("subject", subjectService.findAll());
        return "reports/index";
    }

    @GetMapping("/update")
    @ResponseBody
    public Boolean update(@RequestParam(required = false, value = "type") String type) {
        try {
            reportService.update(type);
            return true;
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return false;
    }

    @DeleteMapping()
    @ResponseBody
    public Boolean remove(DisableRequest disableRequest) {
        try {
            return reportService.remove(disableRequest);
        } catch (Exception e) {
            logger.error(disableRequest.getIds().toString(), e);
        }
        return false;
    }

    @GetMapping("/export")
    public void exportExcel(
            @RequestParam(value = "report", required = false) String reportStr,
            HttpServletResponse httpServletResponse) {
        reportService.exportExcel(reportStr, httpServletResponse);
    }
}
