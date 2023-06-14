package com.tkpm.studentsmanagement.dto;

import java.util.List;

import com.tkpm.studentsmanagement.entity.ClassEntity;
import com.tkpm.studentsmanagement.entity.OtpEntity;
import com.tkpm.studentsmanagement.entity.RoleEntity;
import com.tkpm.studentsmanagement.entity.StudentEntity;
import com.tkpm.studentsmanagement.entity.TestEntity;

public class UserDTO extends AbstractDTO {

    private String name;

    private String username;

    private String password;

    private String email;

    private Boolean isEnable;

    // relationship
    private List<ClassEntity> createdClasses;

    private List<ClassEntity> updatedClasses;

    private List<Configuration> createdConfigurations;

    private List<Configuration> updatedConfigurations;

    private List<StudentEntity> createdStudents;

    private List<StudentEntity> updatedStudents;

    private List<TestEntity> createdTests;

    private List<TestEntity> updatedTests;

    private List<TestEntity> createdUses;

    private List<TestEntity> updatedUsers;

    private List<OtpEntity> createdOtps;

    private List<OtpEntity> updatedOtps;

    private List<RoleEntity> roles;
    // end relationship

    public List<ClassEntity> getCreatedClasses() {
        return this.createdClasses;
    }

    public void setCreatedClasses(List<ClassEntity> createdClasses) {
        this.createdClasses = createdClasses;
    }

    public List<ClassEntity> getUpdatedClasses() {
        return this.updatedClasses;
    }

    public void setUpdatedClasses(List<ClassEntity> updatedClasses) {
        this.updatedClasses = updatedClasses;
    }

    public List<Configuration> getCreatedConfigurations() {
        return this.createdConfigurations;
    }

    public void setCreatedConfigurations(List<Configuration> createdConfigurations) {
        this.createdConfigurations = createdConfigurations;
    }

    public List<Configuration> getUpdatedConfigurations() {
        return this.updatedConfigurations;
    }

    public void setUpdatedConfigurations(List<Configuration> updatedConfigurations) {
        this.updatedConfigurations = updatedConfigurations;
    }

    public List<StudentEntity> getCreatedStudents() {
        return this.createdStudents;
    }

    public void setCreatedStudents(List<StudentEntity> createdStudents) {
        this.createdStudents = createdStudents;
    }

    public List<StudentEntity> getUpdatedStudents() {
        return this.updatedStudents;
    }

    public void setUpdatedStudents(List<StudentEntity> updatedStudents) {
        this.updatedStudents = updatedStudents;
    }

    public List<TestEntity> getCreatedTests() {
        return this.createdTests;
    }

    public void setCreatedTests(List<TestEntity> createdTests) {
        this.createdTests = createdTests;
    }

    public List<TestEntity> getUpdatedTests() {
        return this.updatedTests;
    }

    public void setUpdatedTests(List<TestEntity> updatedTests) {
        this.updatedTests = updatedTests;
    }

    public List<TestEntity> getCreatedUses() {
        return this.createdUses;
    }

    public void setCreatedUses(List<TestEntity> createdUses) {
        this.createdUses = createdUses;
    }

    public List<TestEntity> getUpdatedUsers() {
        return this.updatedUsers;
    }

    public void setUpdatedUsers(List<TestEntity> updatedUsers) {
        this.updatedUsers = updatedUsers;
    }

    public List<OtpEntity> getCreatedOtps() {
        return this.createdOtps;
    }

    public void setCreatedOtps(List<OtpEntity> createdOtps) {
        this.createdOtps = createdOtps;
    }

    public List<OtpEntity> getUpdatedOtps() {
        return this.updatedOtps;
    }

    public void setUpdatedOtps(List<OtpEntity> updatedOtps) {
        this.updatedOtps = updatedOtps;
    }

    public List<RoleEntity> getRoles() {
        return this.roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }
}