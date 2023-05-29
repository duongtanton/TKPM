package com.tkpm.studentsmanagement.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkpm.studentsmanagement.dto.OtpDTO;
import com.tkpm.studentsmanagement.dto.UserDTO;
import com.tkpm.studentsmanagement.service.IEmailService;
import com.tkpm.studentsmanagement.service.impl.OtpService;
import com.tkpm.studentsmanagement.service.impl.UserService;

@Controller
@RequestMapping()
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private TextEncryptor textEncryptor;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${tkpm.app.server.url}")
    private String urlServer;
    @Value("${tkpm.app.time.expired}")
    private Integer timeExpired;

    @GetMapping("/login")
    public String index() {

        return "auth/login";
    }

    // @PostMapping("login")
    // public String login(String username, String password) {
    //     UserDTO userDTO = userService.findByUsername(username);
    //     if (userDTO == null) {
    //         // return username not match

    //         return "auth/login";
    //     }
    //     if (!passwordEncoder.matches(password, userDTO.getPassword())) {
    //         // return password not match
    //         return "auth/login";
    //     }
    //     return "redirect:/";
    // }

    @GetMapping("/forgot-password/{token}")
    public String forgotPassword(@PathVariable("token") String token, Model model) {
        try {
            String decode_token = textEncryptor.decrypt(token);
            OtpDTO otpDTO = objectMapper.readValue(decode_token, OtpDTO.class);
            Timestamp currentTimestamp = Timestamp.from(Instant.now());
            Timestamp futureTimestamp = Timestamp
                    .from(currentTimestamp.toInstant().minus(this.timeExpired, ChronoUnit.MINUTES));

            // if (otpDTO.getCreatedDate().after(futureTimestamp)) {
            model.addAttribute("token", token);
            return "auth/reset-password";
            // }
            // throw new Exception("Time invalid");
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }

    @PostMapping("/forgot-password/reset")
    public String resetPassword(String token, String password) {
        try {
            OtpDTO otpDTO = null;
            String decode_token = textEncryptor.decrypt(token);
            otpDTO = objectMapper.readValue(decode_token, OtpDTO.class);
            otpDTO.setUsed(true);
            otpService.save(otpDTO);
            UserDTO userDTO = objectMapper.readValue(otpDTO.getContent(), UserDTO.class);
            UserDTO userDTODb = userService.findByUsernameAndEmail(userDTO.getUsername(), userDTO.getEmail());
            userDTODb.setPassword(password);
            userService.save(userDTODb);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(String username, String email) {
        UserDTO userDTO = userService.findByUsernameAndEmail(username, email);
        if (userDTO == null) {
            return "auth/login";
        }
        try {
            OtpDTO otpDTO = new OtpDTO();
            otpDTO.setContent(objectMapper.writeValueAsString(userDTO));
            OtpDTO newOtpDTO = otpService.save(otpDTO);
            String token = textEncryptor.encrypt(objectMapper.writeValueAsString(newOtpDTO));
            String url = urlServer + "/forgot-password/" + token;
            emailService.sendEmail(email, "RESET PASSWORD", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // handle error
        return "auth/login";
    }
    @GetMapping("/not-permission")
    public String notPermisson() {
        return "error403";
    }
}
