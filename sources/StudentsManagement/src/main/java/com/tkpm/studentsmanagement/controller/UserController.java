package com.tkpm.studentsmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.configuration.CustomUserAuth;
import com.tkpm.studentsmanagement.dto.*;
import com.tkpm.studentsmanagement.entity.RoleEntity;
import com.tkpm.studentsmanagement.service.IEmailService;
import com.tkpm.studentsmanagement.service.impl.OtpService;
import com.tkpm.studentsmanagement.service.impl.RoleService;
import com.tkpm.studentsmanagement.service.impl.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private TextEncryptor textEncryptor;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${tkpm.app.server.url}")
    private String urlServer;
    @Value("${tkpm.app.time.expired}")
    private Integer timeExpired;

    @GetMapping
    public String index(Model model, SimpleRequest simpleRequest,
                        @RequestParam(required = false, value = "user") String userStr) {
        Pageable pageable = PageRequest.of(simpleRequest.getCurrentPage() - 1, simpleRequest.getPerPage(),
                Sort.by("createdDate").descending());
        UserSearchDTO userSearchDTO = new UserSearchDTO();
        try {
            userSearchDTO = objectMapper.readValue(userStr, UserSearchDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleResponse<UserDTO> simpleResponse = userService.findAll(userSearchDTO, pageable);
        model.addAttribute("res", simpleResponse);
        return "users/index";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO findById(@PathVariable("id") Long id) {
        UserDTO userDTO = null;
        try {
            userDTO = userService.findById(id);
        } catch (Exception e) {
            logger.error(id.toString(), e);
        }
        return userDTO;
    }

    @PostMapping
    @ResponseBody
    public Boolean add(@RequestBody UserCreateDTO userCreateDTO) {
        try {
            CustomUserAuth userPrincipal = (CustomUserAuth) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            UserDTO userDTO = userService.save(userCreateDTO,userPrincipal.getId());
            UserBasicDTO userBasicDTO = new UserBasicDTO();
            BeanUtils.copyProperties(userDTO, userBasicDTO);
            OtpDTO otpDTO = new OtpDTO();
            otpDTO.setContent(objectMapper.writeValueAsString(userBasicDTO));
            otpDTO.setUsed(false);
            otpService.save(otpDTO);
            String token = textEncryptor.encrypt(objectMapper.writeValueAsString(otpDTO));
            String url = urlServer + "/activate-account/" + token;
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
        return true;
    }

    @PatchMapping
    @ResponseBody
    public Boolean update(@RequestBody UserDTO user) {
        Boolean updated = false;
        try {
            updated = userService.update(user);
        } catch (Exception e) {
            logger.error(user.toString(), e);
        }
        return updated;
    }

    @GetMapping("/activate-account/{token}")
    public String activateAccount(@PathVariable("token") String token) {
        try {
            String decode_token = textEncryptor.decrypt(token);
            OtpDTO otpDTO = objectMapper.readValue(decode_token, OtpDTO.class);
            otpDTO = otpService.findByIdAndUsed(otpDTO.getId(), false);
            Timestamp currentTimestamp = Timestamp.from(Instant.now());
            Timestamp futureTimestamp = Timestamp
                    .from(currentTimestamp.toInstant().minus(this.timeExpired, ChronoUnit.MINUTES));
            if (otpDTO.getCreatedDate().after(futureTimestamp)) {
                UserBasicDTO userBasicDTO = objectMapper.readValue(otpDTO.getContent(), UserBasicDTO.class);
                UserDTO userDTO = userService.findByUsernameAndEmail(userBasicDTO.getUsername(), userBasicDTO.getEmail());
                userService.activateAccount(userDTO);
                return "auth/activate-account";
            }
            throw new Exception("Time invalid");
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }

    @DeleteMapping("/disable")
    @ResponseBody
    public Boolean disable(DisableRequest disableRequest) {
        try {
            return userService.disable(disableRequest);
        } catch (Exception e) {
            logger.error(disableRequest.getIds().toString(), e);
        }
        return false;
    }


    @GetMapping("/export")
    public void exportExcel(@RequestParam(value = "ids", required = false) Long[] ids,
                            HttpServletResponse httpServletResponse) {
        System.out.println(List.of(ids));
        userService.exportExcel(ids,httpServletResponse);
    }
}
