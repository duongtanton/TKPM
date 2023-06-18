package com.tkpm.studentsmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.service.impl.ConfigurationService;
import com.tkpm.studentsmanagement.configuration.CustomUserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest,
                        @RequestParam(required = false, value = "configuration") String configurationStr) {
        Pageable pageable = PageRequest.of(simpleRequest.getCurrentPage() - 1, simpleRequest.getPerPage(),
                Sort.by("updatedDate").descending());
        ConfigurationSearchDTO configurationDTO = new ConfigurationSearchDTO();
        if(configurationStr != null && !configurationStr.isEmpty()) {
            try {
                configurationDTO = objectMapper.readValue(configurationStr, ConfigurationSearchDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SimpleResponse<ConfigurationDTO> simpleResponse = configurationService.findAll(configurationDTO, pageable);
        model.addAttribute("res", simpleResponse);
        return "configuration/index";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ConfigurationDTO findById(@PathVariable("id") Long id) {
        ConfigurationDTO configurationDTO = null;
        try {
            configurationDTO = configurationService.findById(id);
        } catch (Exception e) {
            logger.error(id.toString(), e);
        }
        return configurationDTO;
    }

    @PostMapping
    @ResponseBody
    public Boolean add(@RequestBody ConfigurationDTO configurationDTO) {
        try {
            CustomUserAuth configurationPrincipal = (CustomUserAuth) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            configurationService.save(configurationDTO,configurationPrincipal.getId());
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Boolean remove(@PathVariable("id") Long id) {
        try {
            return configurationService.remove(id);
        } catch (Exception e) {
            logger.error(id.toString(), e);
        }
        return false;
    }
}
