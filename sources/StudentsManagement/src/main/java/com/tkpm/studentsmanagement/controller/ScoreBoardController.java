package com.tkpm.studentsmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.service.IScoreBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : daitt
 * @create : 13/6/2023
 **/
@Controller
@RequestMapping("/scoreboard")
public class ScoreBoardController {
    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IScoreBoardService scoreBoardService;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest,
                        @RequestParam(required = false, value = "scoreboard") String scoreboardStr) {
        SimpleResponse<ScoreBoardDTO> simpleResponse = new SimpleResponse<>();
        ScoreBoardDTO scoreBoardDTO;
        try {
            scoreBoardDTO = objectMapper.readValue(scoreboardStr, ScoreBoardDTO.class);
        }
        catch (Exception e) {
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

        List<ScoreBoardDTO> listScoreboards = scoreBoardService.findAll(pageable);
        simpleResponse.setListT(listScoreboards);
        model.addAttribute("res", simpleResponse);
        return "scoreboards/index";
    }

    @PatchMapping
    @ResponseBody
    public Boolean update(ScoreBoardDTO scoreBoardDTO) {
        Boolean updated = false;
        try {
            updated = scoreBoardService.update(scoreBoardDTO);
        }
        catch (Exception e) {
            logger.error(scoreBoardDTO.toString(), e);
        }
        return updated;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ScoreBoardDTO findByID(@PathVariable("id") Long id) {
        ScoreBoardDTO scoreBoardDTO = null;
        try {
            scoreBoardDTO = scoreBoardService.findByID(id);
        }
        catch (Exception e) {
            logger.error(id.toString(), e);
        }
        return scoreBoardDTO;
    }

    @PostMapping
    @ResponseBody
    public Boolean add(ScoreBoardDTO scoreBoardDTO) {
        ScoreBoardDTO newScoreboardDTO = null;
        try {
            newScoreboardDTO = scoreBoardService.create(scoreBoardDTO);
        }
        catch (Exception e) {
            logger.error(scoreBoardDTO.toString(), e);
        }
        return newScoreboardDTO != null;
    }

    @DeleteMapping
    @ResponseBody
    public Boolean delete(DeleteRequest deleteRequest) {
        Boolean deleted = false;
        try {
            deleted = scoreBoardService.delete(deleteRequest.getIds());
        }
        catch (Exception e) {
            logger.error(deleteRequest.getIds().toString(), e);
        }
        return deleted;
    }
}
