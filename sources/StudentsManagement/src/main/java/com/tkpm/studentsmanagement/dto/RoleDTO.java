package com.tkpm.studentsmanagement.dto;

import java.util.List;

public class RoleDTO {
    private Long id;

    private String name;

    private List<UserDTO> users;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getUsers() {
        return this.users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}