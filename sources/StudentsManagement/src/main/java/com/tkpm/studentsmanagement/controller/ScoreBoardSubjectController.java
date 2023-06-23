package com.tkpm.studentsmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.service.IClassService;
import com.tkpm.studentsmanagement.service.IScoreBoardService;
import com.tkpm.studentsmanagement.service.ISubjectService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/scoreboard-subject")
public class ScoreBoardSubjectController {
    private static final Logger logger = LoggerFactory.getLogger(ScoreBoardSubjectController.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IScoreBoardService scoreBoardService;
    @Autowired
    private IClassService classService;
    @Autowired
    private ISubjectService subjectService;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest,
                        @RequestParam(required = false, value = "scoreboard") String scoreboardStr) {
        SimpleResponse<ScoreBoardDTO> simpleResponse = new SimpleResponse<>();
        ScoreBoardSearchDTO scoreBoardSearchDTO = new ScoreBoardSearchDTO();
        try {
            scoreBoardSearchDTO = objectMapper.readValue(scoreboardStr, ScoreBoardSearchDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PageRequest pageable = PageRequest.of(
                simpleRequest.getCurrentPage() - 1,
                simpleRequest.getPerPage(),
                Sort.by("createdDate").descending());
        Integer totalPages = scoreBoardService.totalPages(pageable);

        simpleResponse.setPerPage(pageable.getPageSize());
        simpleResponse.setCurrentPage(pageable.getPageNumber() + 1);
        simpleResponse.setTotalPages(totalPages);

        List<ScoreBoardDTO> listT = scoreBoardService.findAll(scoreBoardSearchDTO,pageable);
        simpleResponse.setListT(listT);
        model.addAttribute("res", simpleResponse);
        model.addAttribute("year", classService.getAllYear());
        model.addAttribute("defaultYear", classService.getAllYear().get(0));
        model.addAttribute("class", classService.findLikeByIdOrNameOrYear(
                null, null, classService.getAllYear().get(0), null));
        model.addAttribute("subject", subjectService.findAll());
        return "scoreboardsubject/index";
    }

    @PatchMapping
    @ResponseBody
    public Boolean update(ScoreBoardDTO scoreboard) {
        Boolean updated = false;
        try {
            updated = scoreBoardService.update(scoreboard);
        } catch (Exception e) {
            logger.error(scoreboard.toString(), e);
        }
        return updated;
    }

    @GetMapping("/static-average-by-class/{classsID}")
    @ResponseBody
    public List<StaticAverageByClass> staticAverageByClass(@PathVariable(value = "classsID") Long classsId) {
        return scoreBoardService.staticAverageByClass(classsId);
    }
    @GetMapping("/{id}")
    @ResponseBody
    public ScoreBoardDTO findByID(@PathVariable("id") Long id) {
        ScoreBoardDTO scoreBoardDTO = null;
        try {
            scoreBoardDTO = scoreBoardService.findByID(id);
        } catch (Exception e) {
            logger.error(id.toString(), e);
        }
        return scoreBoardDTO;
    }

    @PostMapping
    @ResponseBody
    public ScoreBoardDTO add(ScoreBoardDTO scoreboard) {
        try {
            ScoreBoardDTO scoreboardDT0 = scoreBoardService.create(scoreboard);
            return scoreBoardService.updateResult(scoreboardDT0.getId());
        } catch (Exception e) {
            logger.error(scoreboard.toString(), e);
        }
        return null;
    }

    @DeleteMapping
    @ResponseBody
    public Boolean delete(DeleteRequest deleteRequest) {
        Boolean deleted = false;
        try {
            deleted = scoreBoardService.delete(deleteRequest.getIds());
        } catch (Exception e) {
            logger.error(deleteRequest.getIds().toString(), e);
        }
        return deleted;
    }

    @GetMapping("/export")
    public void exportExcel(@RequestParam(value = "scoreboard", required = false) String scoreboardStr,
                            HttpServletResponse httpServletResponse) {
        scoreBoardService.exportExcel(scoreboardStr,httpServletResponse);
    }
}
