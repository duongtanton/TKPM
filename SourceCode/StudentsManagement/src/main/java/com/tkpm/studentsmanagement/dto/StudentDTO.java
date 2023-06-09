package com.tkpm.studentsmanagement.dto;

import java.sql.Date;


public class StudentDTO extends AbstractDTO {
    private String name;

    private Date birthDate;

    private Boolean sex; // male 1: female: 0

    private String address;

    private String email;

    // private List<ClassDTO> classes;

    // private List<TestDTO> tests;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean isSex() {
        return this.sex;
    }

    public Boolean getSex() {
        return this.sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // public List<ClassDTO> getClasses() {
    //     return this.classes;
    // }

    // public void setClasses(List<ClassDTO> classes) {
    //     this.classes = classes;
    // }

    // public List<TestDTO> getTests() {
    //     return this.tests;
    // }

    // public void setTests(List<TestDTO> tests) {
    //     this.tests = tests;
    // }
}
