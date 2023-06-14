package com.tkpm.studentsmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tkpm.studentsmanagement.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    List<UserDTO> findAll();
    SimpleResponse<UserDTO> findAll(UserSearchDTO userSearchDTO, Pageable pageable);
    List<UserDTO> findAllById(List<Long> ids);
    UserDTO findByUsername(String username);
    UserDTO findByUsernameAndEmail(String username, String email);
    UserDTO findById(Long id);
    UserDTO save(UserDTO userDTO);
    UserDTO save(UserCreateDTO userCreateDTO, Long currentUserId) throws JsonProcessingException;
    List<UserDTO> save(List<UserDTO> listStudent);
    Boolean update(UserDTO user);
    void activateAccount(UserDTO userDTO);
    Integer totalPages(Pageable pageable);

    Boolean disable(DisableRequest disableRequest);

    void exportExcel(Long[] ids, HttpServletResponse httpServletResponse);
}