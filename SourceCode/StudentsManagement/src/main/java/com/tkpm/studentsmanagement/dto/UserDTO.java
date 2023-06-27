package com.tkpm.studentsmanagement.dto;

import java.util.List;

public class UserDTO extends AbstractDTO {

    private String name;

    private String username;

    private String password;

    private String email;

    private Boolean isEnable;

    // relationship
    private List<ClassDTO> createdClasses;

    private List<ClassDTO> updatedClasses;

    private List<ConfigurationDTO> createdConfigurations;

    private List<ConfigurationDTO> updatedConfigurations;

    private List<StudentDTO> createdStudents;

    private List<StudentDTO> updatedStudents;

    private List<OtpDTO> createdOtps;

    private List<OtpDTO> updatedOtps;

    private List<RoleDTO> roles;
    // end relationship

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isIsEnable() {
        return this.isEnable;
    }

    public Boolean getIsEnable() {
        return this.isEnable;
    }

    public Boolean getEnable() {
        return this.isEnable;
    }

    public void setEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public List<ClassDTO> getCreatedClasses() {
        return this.createdClasses;
    }

    public void setCreatedClasses(List<ClassDTO> createdClasses) {
        this.createdClasses = createdClasses;
    }

    public List<ClassDTO> getUpdatedClasses() {
        return this.updatedClasses;
    }

    public void setUpdatedClasses(List<ClassDTO> updatedClasses) {
        this.updatedClasses = updatedClasses;
    }

    public List<ConfigurationDTO> getCreatedConfigurations() {
        return this.createdConfigurations;
    }

    public void setCreatedConfigurations(List<ConfigurationDTO> createdConfigurations) {
        this.createdConfigurations = createdConfigurations;
    }

    public List<ConfigurationDTO> getUpdatedConfigurations() {
        return this.updatedConfigurations;
    }

    public void setUpdatedConfigurations(List<ConfigurationDTO> updatedConfigurations) {
        this.updatedConfigurations = updatedConfigurations;
    }

    public List<StudentDTO> getCreatedStudents() {
        return this.createdStudents;
    }

    public void setCreatedStudents(List<StudentDTO> createdStudents) {
        this.createdStudents = createdStudents;
    }

    public List<StudentDTO> getUpdatedStudents() {
        return this.updatedStudents;
    }

    public void setUpdatedStudents(List<StudentDTO> updatedStudents) {
        this.updatedStudents = updatedStudents;
    }

    public List<OtpDTO> getCreatedOtps() {
        return this.createdOtps;
    }

    public void setCreatedOtps(List<OtpDTO> createdOtps) {
        this.createdOtps = createdOtps;
    }

    public List<OtpDTO> getUpdatedOtps() {
        return this.updatedOtps;
    }

    public void setUpdatedOtps(List<OtpDTO> updatedOtps) {
        this.updatedOtps = updatedOtps;
    }

    public List<RoleDTO> getRoles() {
        return this.roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}