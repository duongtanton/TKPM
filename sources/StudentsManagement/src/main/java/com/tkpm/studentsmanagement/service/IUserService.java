package com.tkpm.studentsmanagement.service;

import com.tkpm.studentsmanagement.dto.UserDTO;

public interface IUserService {
    public UserDTO findByUsernameAndEmail(String username, String email);

    public UserDTO findByUsername(String username);

    public UserDTO save(UserDTO userDTO);
}
