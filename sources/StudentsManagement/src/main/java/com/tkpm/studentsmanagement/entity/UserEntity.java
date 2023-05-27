package com.tkpm.studentsmanagement.entity;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity(name = "user")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class UserEntity extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    // relationship
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<ClassEntity> createdClasses;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<ClassEntity> updatedClasses;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Configuration> createdConfigurations;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<Configuration> updatedConfigurations;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<StudentEntity> createdStudents;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<StudentEntity> updatedStudents;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<TestEntity> createdTests;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<TestEntity> updatedTests;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<TestEntity> createdUses;

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    private List<TestEntity> updatedUsers;
    // end relationship

    public List<ClassEntity> getCreatedClasses() {
        return this.createdClasses;
    }

    public void setCreatedClasses(List<ClassEntity> classesCreated) {
        this.createdClasses = classesCreated;
    }

    public List<ClassEntity> getUpdatedClasses() {
        return this.updatedClasses;
    }

    public void setUpdatedClasses(List<ClassEntity> classesUpdated) {
        this.updatedClasses = classesUpdated;
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
}