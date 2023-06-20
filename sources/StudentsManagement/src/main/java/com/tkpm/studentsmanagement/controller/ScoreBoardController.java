package com.tkpm.studentsmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.dto.DeleteRequest;
import com.tkpm.studentsmanagement.dto.ScoreBoardDTO;
import com.tkpm.studentsmanagement.dto.SimpleRequest;
import com.tkpm.studentsmanagement.dto.SimpleResponse;
import com.tkpm.studentsmanagement.service.IScoreBoardService;

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

        List<ScoreBoardDTO> listT = scoreBoardService.findAll(pageable);
        simpleResponse.setListT(listT);
        model.addAttribute("res", simpleResponse);
        return "scoreboards/index";
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
            return scoreBoardService.create(scoreboard);
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
}
