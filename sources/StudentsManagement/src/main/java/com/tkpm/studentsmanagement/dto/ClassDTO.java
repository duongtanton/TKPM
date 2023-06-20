package com.tkpm.studentsmanagement.dto;

import java.util.List;

public class ClassDTO extends AbstractDTO {
    private String name;

    private Integer numberOfPupils;

    private String year;

    // relationship
    private List<StudentDTO> students;

    private List<TestDTO> tests;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPupils() {
        return this.numberOfPupils;
    }

    public void setNumberOfPupils(Integer numberOfPupils) {
        this.numberOfPupils = numberOfPupils;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<StudentDTO> getStudents() {
        return this.students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public List<TestDTO> getTests() {
        return this.tests;
    }

    public void setTests(List<TestDTO> tests) {
        this.tests = tests;
    }

}
